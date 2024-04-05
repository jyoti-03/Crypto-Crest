package com.natwest.userservice.exception;

public class MandatoryFieldException extends Exception{
    public MandatoryFieldException(String message) {
        super(message);
    }
}
