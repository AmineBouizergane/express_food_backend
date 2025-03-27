package com.example.expressfood.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class ErrorMessageFormat {

    private Date timestamp;
    private String message;

}