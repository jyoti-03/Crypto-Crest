package com.stackroute.cryptoserver.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SymbolAlreadyExists extends Exception{

    private String msg;

    public SymbolAlreadyExists(String msg) {
        super(msg);
        this.msg=msg;
    }

}
