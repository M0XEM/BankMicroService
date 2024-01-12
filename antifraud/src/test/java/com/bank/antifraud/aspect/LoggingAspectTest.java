package com.bank.antifraud.aspect;

import com.bank.antifraud.entity.Audit;
import com.bank.antifraud.entity.account.SuspiciousAccountTransfer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Timestamp;

@ExtendWith(MockitoExtension.class)
class LoggingAspectTest {

    @InjectMocks
    private LoggingAspect loggingAspect;

    @Test
    void checkingLoggingProcess() throws Throwable {
        SuspiciousAccountTransfer suspiciousAccountTransfer = getSAT(1, 1, false, false,
                "Не заблокирован","Подозрений нет");

        String entityJson = new ObjectMapper().writeValueAsString(suspiciousAccountTransfer);
        Audit audit = getAudit(0, "SuspiciousAccountTransfer","create", "anti_fraud",
                new Timestamp(System.currentTimeMillis()), null, null, null, entityJson);

        Audit actualResult = loggingAspect.getAuditInfo(suspiciousAccountTransfer, "createSuspiciousAccountTransfer");
        Assertions.assertEquals(audit, actualResult);

    }

    private Audit getAudit(long id, String enType, String opType, String crBy, Timestamp crAt, String modBy,
                            Timestamp modAt, String newEnJSON, String enJSON) {

        return Audit.builder()
                .id(id)
                .operationType(opType)
                .entityType(enType)
                .createdAt(crAt)
                .createdBy(crBy)
                .modifiedAt(modAt)
                .modifiedBy(modBy)
                .entityJson(enJSON)
                .newEntityJson(newEnJSON)
                .build();
    }

    private SuspiciousAccountTransfer getSAT(long id, long transferId, boolean isBlocked, boolean isSuspicious,
                                             String blockedReason, String suspiciousReason) {

        return SuspiciousAccountTransfer.builder()
                .id(id)
                .transferId(transferId)
                .isBlocked(isBlocked)
                .isSuspicious(isSuspicious)
                .blockedReason(blockedReason)
                .suspiciousReason(suspiciousReason)
                .build();
    }

}