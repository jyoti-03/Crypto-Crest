package com.stackroute.cryptoserver.entity;


import jakarta.persistence.Id;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Component
public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String state;
    private String country;
    private String phoneNumber;
    private String email;
    private double accountBalance;
    private List<CryptoInfo> cryptoCurrency;
}
