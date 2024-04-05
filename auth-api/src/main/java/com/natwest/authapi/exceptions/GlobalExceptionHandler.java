package com.natwest.authapi.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;
import java.time.LocalDate;

@RestControllerAdvice
public class GlobalExceptionHandler {
//    @ExceptionHandler(Exception.class)
//    public ProblemDetail handleSecurityException(Exception exception) {
//        ProblemDetail errorDetail = null;
//
//        // TODO send this stack trace to an observability tool
//        exception.printStackTrace();
//
//        if (exception instanceof BadCredentialsException) {
//            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
//            errorDetail.setProperty("description", "The username or password is incorrect");
//
//            return errorDetail;
//        }
//
//        if (exception instanceof AccountStatusException) {
//            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
//            errorDetail.setProperty("description", "The account is locked");
//        }
//
//        if (exception instanceof AccessDeniedException) {
//            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
//            errorDetail.setProperty("description", "You are not authorized to access this resource");
//        }
//
//        if (exception instanceof SignatureException) {
//            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
//            errorDetail.setProperty("description", "The JWT signature is invalid");
//        }
//
//        if (exception instanceof ExpiredJwtException) {
//            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
//            errorDetail.setProperty("description", "The JWT token has expired");
//        }
//
//        if (errorDetail == null) {
//            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
//            errorDetail.setProperty("description", "Unknown internal server error.");
//        }
//
//        return errorDetail;
//    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorInfo> handleBadCredentials(BadCredentialsException exception){
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setHttpStatus(HttpStatus.BAD_REQUEST);
        errorInfo.setErrormessage("Please enter valid username and password...!");
        errorInfo.setDate(LocalDate.now());
        return new  ResponseEntity<>(errorInfo,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountStatusException.class)
    public ResponseEntity<ErrorInfo> handlePasswordException(AccountStatusException exception){
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setHttpStatus(HttpStatus.BAD_REQUEST);
        errorInfo.setErrormessage("Invalid password...!");
        errorInfo.setDate(LocalDate.now());
        return new  ResponseEntity<>(errorInfo,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorInfo> handleAccessDeniedException(AccessDeniedException exception){
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setHttpStatus(HttpStatus.BAD_REQUEST);
        errorInfo.setErrormessage("Please enter the correct password...!");
        errorInfo.setDate(LocalDate.now());
        return new  ResponseEntity<>(errorInfo,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorInfo> handleExpireJWTException(ExpiredJwtException exception){
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setHttpStatus(HttpStatus.BAD_REQUEST);
        errorInfo.setErrormessage("Your Token Has Expired...!");
        errorInfo.setDate(LocalDate.now());
        return new  ResponseEntity<>(errorInfo,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorInfo>handleConstraintViolationException (ConstraintViolationException exception){
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setHttpStatus(HttpStatus.BAD_REQUEST);
        errorInfo.setErrormessage("please enter valid email...!");
        errorInfo.setDate(LocalDate.now());
        return new  ResponseEntity<>(errorInfo,HttpStatus.BAD_REQUEST);
    }


}