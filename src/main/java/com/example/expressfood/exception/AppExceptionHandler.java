package com.example.expressfood.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> HandlerAllException(Exception ex){
        ErrorMessageFormat errorMessages=new ErrorMessageFormat(new Date(),ex.getMessage());
        return new ResponseEntity<>(errorMessages,new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
