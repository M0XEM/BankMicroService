package com.bank.antifraud.handler;

//Объекты этого класса будут конвертироваться в json и отправляться если возникла ошибка
import lombok.Getter;
import lombok.Setter;


@Setter
public class SuspiciousTransferErrorResponse {
    private String message;
    private long timestamp;

    public SuspiciousTransferErrorResponse(long timestamp) {
        this.timestamp = timestamp;
    }
}
