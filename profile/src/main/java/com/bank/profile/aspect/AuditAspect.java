package com.bank.profile.aspect;

import com.bank.profile.aspect.annotation.Auditable;
import com.bank.profile.model.Audit;
import com.bank.profile.repository.AuditRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class AuditAspect {
    private final AuditRepository auditRepository;
    private final ObjectMapper mapper;

    public AuditAspect(AuditRepository auditRepository, ObjectMapper mapper) {
        this.auditRepository = auditRepository;
        this.mapper = mapper;
    }

    @After("@annotation(auditable)")
    public void createNewAudit(JoinPoint joinPoint, Auditable auditable) throws JsonProcessingException {
        String entityJson = getEntityJson(joinPoint);

        Audit auditNewToDb = Audit.builder()
                .entityType(auditable.entityType())
                .operationType(auditable.operationType())
                .createdBy(auditable.createdBy())
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .entityJson(entityJson)
                .build();

        auditRepository.save(auditNewToDb);
    }

    private String getEntityJson(JoinPoint joinPoint) throws JsonProcessingException {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        String[] parameterNames = methodSignature.getParameterNames();

        Map<String, Object> paramMap = new HashMap<>();

        for (int i = 0; i < parameterNames.length; i++) {
            paramMap.put(parameterNames[i], args[i]);
        }

        return mapper.writeValueAsString(paramMap);
    }
}