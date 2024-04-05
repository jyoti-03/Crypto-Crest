package com.natwest.notificationservice.controllerAdvice;

import com.natwest.notificationservice.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleExceptionForUser(UserNotFoundException e){
        ErrorResponse errorInfo=new ErrorResponse();
        errorInfo.setHttpStatus(HttpStatus.BAD_REQUEST);
        errorInfo.setErrorMessage(e.getMessage());
        errorInfo.setLocalDateTime(LocalDateTime.now());
        return new ResponseEntity<>(errorInfo,HttpStatus.BAD_REQUEST);
    }

}
