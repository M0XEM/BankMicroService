package com.bank.antifraud.service;

import com.bank.antifraud.entity.Audit;
import com.bank.antifraud.entity.account.SuspiciousAccountTransfer;
import com.bank.antifraud.repository.AuditRepository;
import com.bank.antifraud.repository.transfer.AccountTransferRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuditServiceTest {
    @Mock
    private AuditRepository auditRepository;
    @InjectMocks
    private AuditService auditService;

    @Test
    void successfulCreate() {
        Audit audit = new Audit();

        auditService.createAudit(audit);

        verify(auditRepository).save(audit);
    }
    @Test
    void successfulRead() {
        auditService.readAll();

        verify(auditRepository).findAll();
    }
}