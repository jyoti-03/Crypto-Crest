package com.stackroute.cryptoserver.entity;


import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class CryptoPrice {
    @Id
    private String symbol;
    @Column(nullable = true)
    private Double rate;
    @Column(nullable = true)
    private Double amount;
    private String timestamp;

}
