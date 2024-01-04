package com.bank.antifraud.controller;

import com.bank.antifraud.entity.account.AccountTransfer;
import com.bank.antifraud.entity.phone.PhoneTransfer;
import com.bank.antifraud.service.SuspiciousAccountTransferService;
import com.bank.antifraud.service.SuspiciousPhoneTransferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SuspiciousPhoneTransferControllerTest {
    @Mock
    private SuspiciousPhoneTransferService suspiciousTransferService;
    @InjectMocks
    private SuspiciousPhoneTransferController suspiciousTransferController;

    @Test
    void checkingTransferCreation() {
        PhoneTransfer transfer = new PhoneTransfer();

        suspiciousTransferController.createTransfer(transfer);

        verify(suspiciousTransferService).doOperationsWithTransfer(transfer);
    }
}