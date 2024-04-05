package com.stackroute.cryptoserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.cryptoserver.entity.CryptoInfo;
import com.stackroute.cryptoserver.entity.ReqBody;
import com.stackroute.cryptoserver.entity.User;
import com.stackroute.cryptoserver.exception.AmountInsufficient;
import com.stackroute.cryptoserver.exception.SymbolNotExists;
import com.stackroute.cryptoserver.repository.CryptoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CryptoServiceIMPTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CryptoRepository cryptoRepository;

    @InjectMocks
    private CryptoServiceIMP cryptoService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        objectMapper = new ObjectMapper();
    }

//    @Test
//    void testFetchDataAndSave() {
//        // Mock response
//        ResponseEntity<String> responseEntity = ResponseEntity.ok("[{\"symbol\":\"BTC\",\"name\":\"Bitcoin\"}]");
//        when(restTemplate.exchange(anyString(), any(), any(), eq(String.class))).thenReturn(responseEntity);
//
//        cryptoService.fetchDataAndSave();
//        verify(cryptoRepository, times(1)).save(any());
//    }
//
//    @Test
//    void testFetchDataFromDB() {
//        // Mock data
//        CryptoInfo cryptoInfo = new CryptoInfo("BTC", "Bitcoin");
//        List<CryptoInfo> cryptoInfos = Arrays.asList(cryptoInfo);
//
//        // Mock repository response
//        when(cryptoRepository.findAll()).thenReturn(cryptoInfos);
//
//        // Test method
//        List<CryptoInfo> result = cryptoService.fetchDataFromDB();
//
//        // Verify result
//        assertEquals(cryptoInfos, result);
//    }
//
//    @Test
//    void testDeleteAllFromDB() {
//        // Test method
//        cryptoService.deleteAllFromDB();
//
//        // Verify that deleteAll method is called
//        verify(cryptoRepository, times(1)).deleteAll();
//    }
//
//    @Test
//    void testGetDataBySymbolFromDB() {
//        // Mock data
//        CryptoInfo cryptoInfo = new CryptoInfo("BTC", "Bitcoin");
//
//        // Mock repository response
//        when(cryptoRepository.findById("BTC")).thenReturn(java.util.Optional.of(cryptoInfo));
//
//        // Test method
//        CryptoInfo result = cryptoService.getDataBySymbolFromDB("BTC");
//
//        // Verify result
//        assertEquals(cryptoInfo, result);
//    }
//
//    @Test
//    void testGetPriceBySymbol() throws JsonProcessingException {
//        // Mock response
//        String response = "{\"symbol\":\"BTC\",\"rate\":50000}";
//        ResponseEntity<String> responseEntity = ResponseEntity.ok(response);
//        when(restTemplate.exchange(anyString(), any(), any(), eq(String.class))).thenReturn(responseEntity);
//
//        // Test method
//        Object result = cryptoService.getPriceBySymbol("BTC");
//
//        // Verify result
//        assertEquals(response, result);
//    }
//
//    @Test
//    void testBuyCryptoCurrency() throws JsonProcessingException, SymbolNotExists, AmountInsufficient {
//        // Mock data
//        ReqBody body = new ReqBody("BTC", 2);
//
//        // Mock response
//        String response = "{\"symbol\":\"BTC\",\"rate\":50000}";
//        ResponseEntity<String> responseEntity = ResponseEntity.ok(response);
//        when(restTemplate.exchange(anyString(), any(), any(), eq(String.class))).thenReturn(responseEntity);
//
//        // Mock repository response
//        CryptoInfo cryptoInfo = new CryptoInfo("BTC", "Bitcoin");
//        when(cryptoRepository.findById("BTC")).thenReturn(java.util.Optional.of(cryptoInfo));
//
//        // Set user account balance
//        User user = new User();
//        user.setAccountBalance(100000);
//        cryptoService.setUser(user);
//
//        // Test method
//        CryptoInfo result = cryptoService.buyCryptoCurrency(body);
//
//        // Verify result
//        assertNotNull(result);
//    }
//
//    @Test
//    void testSellCryptoCurrency() throws JsonProcessingException, SymbolNotExists, AmountInsufficient {
//        // Mock data
//        ReqBody body = new ReqBody("BTC", 1);
//
//        // Mock response
//        String response = "{\"symbol\":\"BTC\",\"rate\":50000}";
//        ResponseEntity<String> responseEntity = ResponseEntity.ok(response);
//        when(restTemplate.exchange(anyString(), any(), any(), eq(String.class))).thenReturn(responseEntity);
//
//        // Mock repository response
//        CryptoInfo cryptoInfo = new CryptoInfo("BTC", "Bitcoin");
//        when(cryptoRepository.findById("BTC")).thenReturn(java.util.Optional.of(cryptoInfo));
//
//        // Set user account balance and owned cryptocurrency
//        User user = new User();
//        user.setAccountBalance(0);
//        user.setCryptoCurrency(new CryptoInfo[]{cryptoInfo});
//        cryptoService.setUser(user);
//
//        // Test method
//        CryptoInfo result = cryptoService.sellCryptoCurrency(body);
//
//        // Verify result
//        assertNotNull(result);
//    }
//
//    // Add similar tests for other methods
}
