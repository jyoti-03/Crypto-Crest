package com.natwest.authapi.exceptions;

import org.springframework.http.HttpStatus;

import java.time.LocalDate;


public class ErrorInfo {
    private String errormessage;
    private HttpStatus httpStatus;
    private LocalDate date;

    public String getErrormessage() {
        return errormessage;
    }

    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ErrorInfo() {
    }

    public ErrorInfo(String errormessage, HttpStatus httpStatus, LocalDate date) {
        this.errormessage = errormessage;
        this.httpStatus = httpStatus;
        this.date = date;
    }

    @Override
    public String toString() {
        return "ErrorInfo{" +
                "errormessage='" + errormessage + '\'' +
                ", httpStatus=" + httpStatus +
                ", date=" + date +
                '}';
    }
}
