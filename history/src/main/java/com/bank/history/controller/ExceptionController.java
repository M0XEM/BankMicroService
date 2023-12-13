package com.bank.history.controller;

import com.bank.history.exceptions.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(EntityNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
