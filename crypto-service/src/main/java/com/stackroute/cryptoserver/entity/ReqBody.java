package com.stackroute.cryptoserver.entity;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReqBody {
     private int userId;
     private String symbol;
     private int quantity;
}
