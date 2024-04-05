package com.stackroute.cryptoserver.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNotExistsException extends Exception{

    private String msg;

    public UserNotExistsException(String msg) {
        super(msg);
        this.msg=msg;
    }
}
