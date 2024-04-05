package com.stackroute.cryptoserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stackroute.cryptoserver.entity.CryptoInfo;
import com.stackroute.cryptoserver.entity.ReqBody;
import com.stackroute.cryptoserver.exception.AmountInsufficient;
import com.stackroute.cryptoserver.exception.SymbolAlreadyExists;
import com.stackroute.cryptoserver.exception.SymbolNotExists;
import com.stackroute.cryptoserver.exception.UserNotExistsException;

import java.util.List;

public interface CryptoService {
    public void fetchDataAndSave() throws SymbolAlreadyExists;
    public List<CryptoInfo> fetchDataFromDB();
    public void deleteAllFromDB();
    public CryptoInfo getDataBySymbolFromDB(String sym) throws SymbolNotExists;
    public Object getPriceBySymbol(String symbolPair) throws SymbolNotExists, JsonProcessingException;
    public String buyCryptoCurrency(ReqBody body) throws JsonProcessingException, SymbolNotExists, AmountInsufficient, UserNotExistsException;
    public String sellCryptoCurrency(ReqBody body) throws SymbolNotExists, AmountInsufficient, JsonProcessingException;
    public String getUserCryptoCollaction();
}
