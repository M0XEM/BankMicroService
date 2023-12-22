package com.bank.profile.exception;

public class NotFoundExceptionEntity extends RuntimeException{
    public NotFoundExceptionEntity(String message) {
        super(message);
    }
}
