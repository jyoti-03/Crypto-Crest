package com.stackroute.cryptoserver.repository;

import com.stackroute.cryptoserver.entity.CryptoInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoRepository extends JpaRepository<CryptoInfo, String> {}
