package com.stackroute.cryptoserver.exception;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SymbolNotExists extends Exception {
    private String msg;

    public SymbolNotExists(String msg) {
        super(msg);
        this.msg=msg;
    }
}
