package com.stackroute.cryptoserver.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.stackroute.cryptoserver.entity.CryptoInfo;
import com.stackroute.cryptoserver.entity.CryptoPrice;
import com.stackroute.cryptoserver.entity.ReqBody;
import com.stackroute.cryptoserver.exception.AmountInsufficient;
import com.stackroute.cryptoserver.exception.SymbolAlreadyExists;
import com.stackroute.cryptoserver.exception.SymbolNotExists;
import com.stackroute.cryptoserver.service.CryptoServiceIMP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/crypto")
public class CryptoController {

    @Autowired
    private CryptoServiceIMP cryptoServiceIMP;


    @PostMapping("/fetch-and-save")
    public ResponseEntity<String> fetchAndSaveCryptoData() {
        try {
            cryptoServiceIMP.fetchDataAndSave();
            return new ResponseEntity<>("Crypto data fetched and saved successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching or saving crypto data: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public List<CryptoInfo> showAllDataFromDB(){
        return cryptoServiceIMP.fetchDataFromDB();
    }

    @DeleteMapping
    public String deleteAllFromDB(){
        cryptoServiceIMP.deleteAllFromDB();
        return "Successfully Deleted From DB";
    }

    @GetMapping("/symbol/{sym}/{sym2}")
    public ResponseEntity<CryptoInfo> getCryptoBySymbol(@PathVariable String sym, @PathVariable String sym2) throws SymbolNotExists {
        String symbolPair = sym + "/" + sym2;
        return ResponseEntity.ok(cryptoServiceIMP.getDataBySymbolFromDB(symbolPair));
    }
    @GetMapping("/price/{sym}/{sym2}")
    public ResponseEntity<Object> getAmountOfSymbol(@PathVariable String sym, @PathVariable String sym2) throws SymbolNotExists, JsonProcessingException {
        String symbolPair = sym + "/" + sym2;
        return ResponseEntity.ok(cryptoServiceIMP.getPriceBySymbol(symbolPair));
    }
    @PostMapping("/buy")
    public ResponseEntity<?> buyCryptoCurrency(@RequestBody ReqBody body) {
        try {
            String boughtCrypto = cryptoServiceIMP.buyCryptoCurrency(body);
            return new ResponseEntity<>(boughtCrypto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error buying cryptocurrency: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/sell")
    public ResponseEntity<?> sellCryptoCurrency(@RequestBody ReqBody body) {
        try {
            String soldCrypto = cryptoServiceIMP.sellCryptoCurrency(body);
            return new ResponseEntity<>(soldCrypto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error selling cryptocurrency: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/user")
    public ResponseEntity<?> getUserCryptoList(){
        String userCrypto = cryptoServiceIMP.getUserCryptoCollaction();
        return new ResponseEntity<>(userCrypto, HttpStatus.OK);
    }

}
