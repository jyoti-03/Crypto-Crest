package com.stackroute.cryptoserver.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmountInsufficient extends Exception{

    private String msg;

    public AmountInsufficient(String msg) {
        super(msg);
        this.msg=msg;
    }

}
