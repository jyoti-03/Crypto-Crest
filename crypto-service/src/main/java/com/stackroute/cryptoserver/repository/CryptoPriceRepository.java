package com.stackroute.cryptoserver.repository;

import com.stackroute.cryptoserver.entity.CryptoPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoPriceRepository extends JpaRepository<CryptoPrice, String> {

}
