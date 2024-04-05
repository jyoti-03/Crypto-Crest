package com.stackroute.cryptoserver.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CryptoInfo {
    @Id
    private String symbol;
    private List<String> available_exchanges;
    private String currency_base;
    private String currency_quote;

}
