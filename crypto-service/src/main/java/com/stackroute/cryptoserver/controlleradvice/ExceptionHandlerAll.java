package com.stackroute.cryptoserver.controlleradvice;


import com.stackroute.cryptoserver.exception.AmountInsufficient;
import com.stackroute.cryptoserver.exception.SymbolAlreadyExists;
import com.stackroute.cryptoserver.exception.SymbolNotExists;
import com.stackroute.cryptoserver.exception.UserNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;

@RestControllerAdvice
public class ExceptionHandlerAll {

    @ExceptionHandler(SymbolAlreadyExists.class)
    public ResponseEntity<ErrorINFO> handleExceptionForSymbolAlreadyExists(SymbolAlreadyExists exception){
        ErrorINFO errorINFO = new ErrorINFO();

        errorINFO.setHttpStatus(HttpStatus.BAD_REQUEST);
        errorINFO.setErrorMsg(exception.getMsg());
        errorINFO.setLocalDate(LocalDate.now());

        return new ResponseEntity<>(errorINFO, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(SymbolNotExists.class)
    public ResponseEntity<ErrorINFO> handleExceptionForSymbolNotExists(SymbolNotExists exception){
        ErrorINFO errorINFO = new ErrorINFO();

        errorINFO.setHttpStatus(HttpStatus.BAD_REQUEST);
        errorINFO.setErrorMsg(exception.getMsg());
        errorINFO.setLocalDate(LocalDate.now());

        return new ResponseEntity<>(errorINFO, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AmountInsufficient.class)
    public ResponseEntity<ErrorINFO> handleExceptionForAmountInsufficient(AmountInsufficient exception){
        ErrorINFO errorINFO = new ErrorINFO();

        errorINFO.setHttpStatus(HttpStatus.BAD_REQUEST);
        errorINFO.setErrorMsg(exception.getMsg());
        errorINFO.setLocalDate(LocalDate.now());

        return new ResponseEntity<>(errorINFO, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserNotExistsException.class)
    public ResponseEntity<ErrorINFO> handleExceptionForUserNotExistsException(UserNotExistsException exception){
        ErrorINFO errorINFO = new ErrorINFO();

        errorINFO.setHttpStatus(HttpStatus.BAD_REQUEST);
        errorINFO.setErrorMsg(exception.getMsg());
        errorINFO.setLocalDate(LocalDate.now());

        return new ResponseEntity<>(errorINFO, HttpStatus.BAD_REQUEST);
    }

}
