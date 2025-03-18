package com.bank.account.exception;

public class NegativeBalanceNotAllowedException extends RuntimeException {
    public NegativeBalanceNotAllowedException(String message) {
        super(message);
    }
}
