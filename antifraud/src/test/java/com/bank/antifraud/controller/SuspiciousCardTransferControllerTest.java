package com.bank.antifraud.controller;

import com.bank.antifraud.entity.card.CardTransfer;
import com.bank.antifraud.service.SuspiciousCardTransferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SuspiciousCardTransferControllerTest {
    @Mock
    private SuspiciousCardTransferService suspiciousTransferService;
    @InjectMocks
    private SuspiciousCardTransferController suspiciousTransferController;

    @Test
    void checkingTransferCreation() {
        CardTransfer transfer = new CardTransfer();

        suspiciousTransferController.createTransfer(transfer);

        verify(suspiciousTransferService).doOperationsWithTransfer(transfer);
    }
}