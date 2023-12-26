package com.bank.antifraud.handler;

//Объекты этого класса будут конвертироваться в json и отправляться если возникла ошибка
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class SuspiciousAccountTransferErrorResponse {
    private String message;
    private long timestamp;

    public SuspiciousAccountTransferErrorResponse(long timestamp) {
        this.timestamp = timestamp;
    }
}
