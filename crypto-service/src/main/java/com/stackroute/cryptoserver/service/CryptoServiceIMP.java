package com.stackroute.cryptoserver.service;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.cryptoserver.entity.CryptoInfo;
import com.stackroute.cryptoserver.entity.CryptoPrice;
import com.stackroute.cryptoserver.entity.ReqBody;
import com.stackroute.cryptoserver.entity.User;
import com.stackroute.cryptoserver.exception.AmountInsufficient;
import com.stackroute.cryptoserver.exception.SymbolNotExists;
import com.stackroute.cryptoserver.exception.UserNotExistsException;
import com.stackroute.cryptoserver.repository.CryptoPriceRepository;
import com.stackroute.cryptoserver.repository.CryptoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service
public class CryptoServiceIMP implements CryptoService {
    @Value("${crypto.url}")
    private String URL_CRYPTO;
    @Value("${price.url}")
    private String URL_PRICE;

    @Value("${api.key}")
    private String API_KEY;

    private final CryptoPriceRepository cryptoPriceRepo;
    private final RestTemplate restTemplate;
    private final CryptoRepository cryptoRepo;
    private final ObjectMapper objectMapper;
    private static User demoUser;

    @Autowired
    public CryptoServiceIMP(RestTemplate restTemplate, CryptoRepository cryptoRepo, ObjectMapper objectMapper, CryptoPriceRepository cryptoPriceRepo) {
        this.restTemplate = restTemplate;
        this.cryptoRepo = cryptoRepo;
        this.objectMapper = objectMapper;
        this.cryptoPriceRepo =cryptoPriceRepo;
        this.demoUser = new User(3, "Jyotirani", "Behera", LocalDate.parse("2003-11-16"), "F", "Odisha", "India", "1122445566", "jyoti@gmail.com", 20_000, new ArrayList<>());
    }
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-host", "api.twelvedata.com");
        headers.set("x-rapidapi-key", API_KEY);
        return headers;
    }
    private ResponseEntity<String> fetchDataFromAPI(String endpoint) {
        HttpHeaders headers = createHeaders();
        try {
            return restTemplate.exchange(endpoint, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        } catch (Exception e) {
            handleException("Exception when calling the URL", e);
            return null;
        }
    }
    private void handleException(String message, Exception e) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, message, e);
    }
    @Override
    public void fetchDataAndSave() {
        ResponseEntity<String> responseEntity = fetchDataFromAPI(URL_CRYPTO);
        String responseBody = responseEntity.getBody();
        List<CryptoInfo> cryptoInfos = JsonUtil.parseJsonToList(responseBody, new TypeReference<List<CryptoInfo>>() {});
        cryptoInfos.forEach(crypto -> {
            if (!cryptoRepo.existsById(crypto.getSymbol())) {
                cryptoRepo.save(crypto);
            }
        });
    }
    @Override
    public List<CryptoInfo> fetchDataFromDB() {
        return cryptoRepo.findAll();
    }
    @Override
    public void deleteAllFromDB() {
        cryptoRepo.deleteAll();
    }
    @Override
    public CryptoInfo getDataBySymbolFromDB(String symbol) throws SymbolNotExists {
        return cryptoRepo.findById(symbol).orElseThrow(() -> new SymbolNotExists("Symbol not found: " + symbol));
    }
    @Override
    public String getPriceBySymbol(String symbolPair) throws JsonProcessingException {
        CryptoPrice priceObj = cryptoPriceRepo.findById(symbolPair).orElse(null);
        if (priceObj == null) return "Price data not found for symbol: " + symbolPair;
        JsonNode jsonData = objectMapper.readTree(objectMapper.writeValueAsString(priceObj));
        double amount = jsonData.hasNonNull("amount") ? jsonData.get("amount").asDouble() : priceObj.getRate();
        return "Symbol: " + priceObj.getSymbol() + "\n" + "Price: " + amount;
    }
    @Override
    public String buyCryptoCurrency(ReqBody body) {
        DecimalFormat df = new DecimalFormat("#.00");
        try {
            if (body.getUserId() != demoUser.getUserId()) {
                throw new UserNotExistsException("User ID not found: " + body.getUserId());
            }
            CryptoPrice priceObj = cryptoPriceRepo.findById(body.getSymbol()).orElseThrow(() ->
                    new SymbolNotExists("Data not found for symbol: " + body.getSymbol())
            );
            double userAmount = demoUser.getAccountBalance();
            int quantity = body.getQuantity();
            JsonNode jsonData = objectMapper.readTree(objectMapper.writeValueAsString(priceObj));
            double amount = jsonData.hasNonNull("amount") ? jsonData.get("amount").asDouble() : priceObj.getRate();
            List<CryptoInfo> buyData = new ArrayList<>();
            if ((amount * quantity) < userAmount && userAmount > 0) {
                for (int i = 0; i < quantity; i++) {
                    CryptoInfo data = getDataBySymbolFromDB(body.getSymbol());
                    buyData.add(data);
                }
                demoUser.getCryptoCurrency().addAll(buyData);
                demoUser.setAccountBalance(Double.parseDouble(df.format(demoUser.getAccountBalance() - (amount * quantity))));
                return String.format("Successfully purchased Crypto Currency.\nUser ID: %d\nCrypto(Symbol): %s\nQuantity: %d\nAvailable balance: %.2f",
                        demoUser.getUserId(), body.getSymbol(), quantity, demoUser.getAccountBalance());
            } else {
                throw new AmountInsufficient("Insufficient funds to complete the purchase.");
            }
        } catch (UserNotExistsException | SymbolNotExists | AmountInsufficient | JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    @Override
    public String sellCryptoCurrency(ReqBody body) {
        DecimalFormat df = new DecimalFormat("#.00");
        try {
            if (body.getUserId() != demoUser.getUserId()) {
                throw new UserNotExistsException("User ID not found: " + body.getUserId());
            }
            CryptoPrice priceObj = cryptoPriceRepo.findById(body.getSymbol()).orElse(null);
            if (priceObj == null) {
                return "Data not found for symbol: " + body.getSymbol();
            }
            double userAmount = demoUser.getAccountBalance();
            int quantity = body.getQuantity();
            JsonNode jsonData = objectMapper.readTree(objectMapper.writeValueAsString(priceObj));
            double amount = jsonData.hasNonNull("amount") ? jsonData.get("amount").asDouble() : priceObj.getRate();
            List<CryptoInfo> userOwnedCryptos = demoUser.getCryptoCurrency().stream()
                    .filter(crypto -> crypto.getSymbol().equals(body.getSymbol()))
                    .collect(Collectors.toList());
            if (userOwnedCryptos.size() <= quantity) {
                throw new AmountInsufficient("You do not own enough cryptocurrency to sell.");
            }
            double salesRevenue = amount * quantity;
            demoUser.setAccountBalance(Double.parseDouble(df.format(demoUser.getAccountBalance() + salesRevenue)));
            Iterator<CryptoInfo> iterator = userOwnedCryptos.iterator();
            int removedCount = 0;
            while (iterator.hasNext() && removedCount < quantity) {
                CryptoInfo crypto = iterator.next();
                if (crypto.getSymbol().equals(body.getSymbol())) {
                    iterator.remove();
                    removedCount++;
                }
            }
            demoUser.setCryptoCurrency(userOwnedCryptos);
            return "Successfully sold cryptocurrency.\n" +
                    "Symbol: " + body.getSymbol() + "\n" +
                    "Quantity Sold: " + quantity + "\n" +
                    "Sales Revenue: " + df.format(salesRevenue) + "\n" +
                    "Available Balance: " + demoUser.getAccountBalance();
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing JSON response", e);
        } catch (AmountInsufficient e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (UserNotExistsException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String getUserCryptoCollaction() {
        List<CryptoInfo> userList = demoUser.getCryptoCurrency();
        Map<String, Long> symbolCounts = userList.stream()
                .collect(Collectors.groupingBy(CryptoInfo::getSymbol, Collectors.counting()));
        return "User ID: " + demoUser.getUserId() + "\nCrypto Collection: " + symbolCounts.toString() +"\n" + "Available Balance: " + demoUser.getAccountBalance();
    }
}

