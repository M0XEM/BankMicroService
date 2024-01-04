package com.bank.antifraud.aspect;

import com.bank.antifraud.entity.Audit;
import com.bank.antifraud.entity.account.SuspiciousAccountTransfer;
import com.bank.antifraud.service.AuditService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LoggingAspectTest {

//    @Mock
//    private AuditService auditService;
//    @Mock
//    private ObjectMapper objectMapper;

    @Mock
    private MethodSignature signature;
    @Mock
    private ProceedingJoinPoint joinPoint;
    @InjectMocks
    private LoggingAspect loggingAspect;

    @Test
    void checkingLoggingProcess() throws Throwable {
        SuspiciousAccountTransfer suspiciousAccountTransfer = getSAT(1, 1, false, false,
                "Не заблокирован","Подозрений нет");

        String entityJson = new ObjectMapper().writeValueAsString(suspiciousAccountTransfer);
        Audit audit = getAudit(0, "SuspiciousAccountTransfer","create", "anti_fraud",
                new Timestamp(System.currentTimeMillis()), null, null, null, entityJson);

        Mockito.doReturn(suspiciousAccountTransfer).when(joinPoint).proceed();
        Mockito.doReturn(signature).when(joinPoint).getSignature();
        Mockito.doReturn("createSuspiciousAccountTransfer").when(signature).getName();

        Audit actualResult = loggingAspect.getAuditInfo(joinPoint);
        System.out.println(Math.abs((audit.getCreatedAt().getTime() - actualResult.getCreatedAt().getTime())));

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