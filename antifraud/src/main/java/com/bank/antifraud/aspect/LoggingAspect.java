package com.bank.antifraud.aspect;

import com.bank.antifraud.entity.Audit;
import com.bank.antifraud.service.AuditService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@Aspect
public class LoggingAspect {

    private final AuditService auditService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public LoggingAspect(AuditService auditService) {
        this.auditService = auditService;
    }

//    @Autowired
//    public LoggingAspect(AuditService auditService, ObjectMapper objectMapper) {
//        this.auditService = auditService;
//        this.objectMapper = objectMapper;
//    }

    @Pointcut("execution(* com.bank.antifraud.controller.SuspiciousAccountTransferController.*(..))")
    private void account() {}

    @Pointcut("execution(* com.bank.antifraud.controller.SuspiciousCardTransferController.*(..))")
    private void card() {}

    @Pointcut("execution(* com.bank.antifraud.controller.SuspiciousPhoneTransferController.*(..))")
    private void phone() {}

    @Pointcut("execution(* read*(..))")
    private void readMethods() {}

    @Pointcut("(account() || phone() || card()) && !readMethods()")
    private void allSuspiciousMethods() {}

    @Around("allSuspiciousMethods()")
    public Object beforeAnyCudMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Audit audit = getAuditInfo(proceedingJoinPoint);

        auditService.createAudit(audit);

        return proceedingJoinPoint.proceed();
    }


    public Audit getAuditInfo(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object targetMethodResult = proceedingJoinPoint.proceed();
        String methodName = proceedingJoinPoint.getSignature().getName();

        //тип операции назначается из соображения, что при масштабировании приложения названия методов будут начинаться с действия
        int firstUpperCaseIndex = 0;
        for (int i = 0; i < methodName.length(); i++) {
            if (Character.isUpperCase(methodName.charAt(i))) {
                firstUpperCaseIndex = i;
                break;
            }
        }
        String operationType = methodName.substring(0, firstUpperCaseIndex);

        Audit audit = null;
        try {
            audit = Audit.builder()
                    .entityType(targetMethodResult.getClass().getSimpleName())
                    .operationType(operationType)
                    .createdBy("anti_fraud")
                    .createdAt(new Timestamp(System.currentTimeMillis()))
                    .modifiedBy(null)
                    .modifiedAt(null)
                    .newEntityJson(null)
                    .entityJson(objectMapper.writeValueAsString(targetMethodResult))
                    .build();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return audit;
    }
}
