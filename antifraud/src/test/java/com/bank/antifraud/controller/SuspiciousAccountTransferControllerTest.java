package com.bank.antifraud.controller;

import com.bank.antifraud.entity.account.AccountTransfer;
import com.bank.antifraud.entity.account.SuspiciousAccountTransfer;
import com.bank.antifraud.service.SuspiciousAccountTransferService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SuspiciousAccountTransferControllerTest {
    @Mock
    private SuspiciousAccountTransferService suspiciousTransferService;
    @InjectMocks
    private SuspiciousAccountTransferController suspiciousTransferController;

    @Test
    void checkingTransferCreation() {
        AccountTransfer transfer = new AccountTransfer();

        suspiciousTransferController.createTransfer(transfer);

        verify(suspiciousTransferService).doOperationsWithTransfer(transfer);
    }

    @Test
    void checkingSuspiciousTransferCreationOrUpdating() {
        SuspiciousAccountTransfer suspiciousTransfer = new SuspiciousAccountTransfer();

        suspiciousTransferController.createSuspiciousAccountTransfer(suspiciousTransfer);
        suspiciousTransferController.updateSuspiciousAccountTransfer(suspiciousTransfer);

        verify(suspiciousTransferService).create(suspiciousTransfer);
        verify(suspiciousTransferService).update(suspiciousTransfer);
    }

    @Test
    void checkingSuspiciousTransferReading() {
        suspiciousTransferController.readSuspiciousAccountTransfer(1L);
        suspiciousTransferController.readAllSuspiciousAccountTransfers();

        verify(suspiciousTransferService).read(1L);
        verify(suspiciousTransferService).readAll();
    }

    @Test
    void checkingSuspiciousTransferDeleting() {
        suspiciousTransferController.deleteSuspiciousAccountTransfer(1L);

        verify(suspiciousTransferService).delete(1L);
    }

}