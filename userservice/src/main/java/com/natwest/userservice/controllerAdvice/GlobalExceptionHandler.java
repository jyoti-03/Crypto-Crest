package com.natwest.userservice.controllerAdvice;

import com.natwest.userservice.exception.InvalidAgeException;
import com.natwest.userservice.exception.MandatoryFieldException;
import com.natwest.userservice.exception.UserAlreadyExistException;
import com.natwest.userservice.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleExceptionForUser(UserAlreadyExistException e) {
        ErrorResponse errorInfo = new ErrorResponse();
        errorInfo.setHttpStatus(HttpStatus.BAD_REQUEST);
        errorInfo.setErrorMessage(e.getMessage());
        errorInfo.setLocalDateTime(LocalDateTime.now());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidAgeException.class)
    public ResponseEntity<ErrorResponse> handleExceptionForInvalidAge(InvalidAgeException e) {
        ErrorResponse errorInfo = new ErrorResponse();
        errorInfo.setHttpStatus(HttpStatus.BAD_REQUEST);
        errorInfo.setErrorMessage(e.getMessage());
        errorInfo.setLocalDateTime(LocalDateTime.now());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(MandatoryFieldException.class)
    public ResponseEntity<ErrorResponse> handleMandatoryField(MandatoryFieldException e) {
        ErrorResponse errorInfo = new ErrorResponse();
        errorInfo.setHttpStatus(HttpStatus.BAD_REQUEST);
        errorInfo.setErrorMessage(e.getMessage());
        errorInfo.setLocalDateTime(LocalDateTime.now());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
}
