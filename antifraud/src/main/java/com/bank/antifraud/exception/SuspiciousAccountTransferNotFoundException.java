package com.bank.antifraud.exception;

public class SuspiciousAccountTransferNotFoundException extends RuntimeException {
    public SuspiciousAccountTransferNotFoundException(String message) {
        super(message);
    }
}
