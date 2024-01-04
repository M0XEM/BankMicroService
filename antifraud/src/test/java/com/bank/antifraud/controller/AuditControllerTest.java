package com.bank.antifraud.controller;

import com.bank.antifraud.service.AuditService;
import com.bank.antifraud.service.SuspiciousAccountTransferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)

class AuditControllerTest {
    @Mock
    private AuditService auditService;
    @InjectMocks
    private AuditController auditController;

    @Test
    void checkingSuspiciousTransferReading() {
        auditController.readAudit();

        verify(auditService).readAll();
    }
}