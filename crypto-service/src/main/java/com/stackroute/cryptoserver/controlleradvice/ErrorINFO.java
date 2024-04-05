package com.stackroute.cryptoserver.controlleradvice;

import org.springframework.http.HttpStatus;

import java.time.LocalDate;

public class ErrorINFO {
    private String errorMsg;
    private HttpStatus httpStatus;
    private LocalDate localDate;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public ErrorINFO() {
    }

    public ErrorINFO(String errorMsg, HttpStatus httpStatus, LocalDate localDate) {
        this.errorMsg = errorMsg;
        this.httpStatus = httpStatus;
        this.localDate = localDate;
    }
}
