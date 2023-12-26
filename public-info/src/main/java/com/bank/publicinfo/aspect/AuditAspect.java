package com.bank.publicinfo.aspect;

import com.bank.publicinfo.aspect.annotation.Auditable;
import com.bank.publicinfo.entity.AuditEntity;
import com.bank.publicinfo.repository.AuditRepository;
import com.bank.publicinfo.service.interfaces.AtmService;
import com.bank.publicinfo.service.interfaces.BankDetailsService;
import com.bank.publicinfo.service.interfaces.BranchService;
import com.bank.publicinfo.service.interfaces.CertificateService;
import com.bank.publicinfo.service.interfaces.LicenseService;
import com.bank.publicinfo.service.interfaces.MainService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@Aspect
@Slf4j
@Transactional
public class AuditAspect {
    private final AuditRepository auditRepository;
    private final HashMap<String, MainService> serviceHashMap;

    public AuditAspect(AuditRepository auditRepository,
                       AtmService atmService,
                       BankDetailsService bankDetailsService,
                       BranchService branchService,
                       CertificateService certificateService,
                       LicenseService licenseService,
                       HashMap<String, MainService> serviceHashMap) {
        this.auditRepository = auditRepository;
        serviceHashMap.put("atm", atmService);
        serviceHashMap.put("bank", bankDetailsService);
        serviceHashMap.put("branch", branchService);
        serviceHashMap.put("certificate", certificateService);
        serviceHashMap.put("license", licenseService);
        this.serviceHashMap = serviceHashMap;
    }

    @Around(value = "@annotation(auditable)", argNames = "joinPoint, auditable")
    public void aroundAllAuditableMethods(ProceedingJoinPoint joinPoint, Auditable auditable) throws Throwable {
        Map<String, Object> methodParameters = getParametersFromJoinPoint(joinPoint);
        AuditEntity audit;
        try {
            audit = AuditEntity.builder()
                    .entityType(auditable.entityType())
                    .operationType(auditable.operationType())
                    .createdBy("Creating user")
                    .createdAt(new Timestamp(System.currentTimeMillis()))
                    .entityJson("")
                    .build();
            switch (auditable.operationType()) {
                case "save" -> {
                    ResponseEntity resultObject = (ResponseEntity) joinPoint.proceed();
                    audit.setEntityJson(Objects.requireNonNull(resultObject.getBody()).toString());
                }
                case "delete", "update" -> {
                    audit.setCreatedBy(getCreatedAuditFromEntityId(auditable, (Long) methodParameters.get("id"))
                            .getCreatedBy());
                    audit.setCreatedAt(getCreatedAuditFromEntityId(auditable, (Long) methodParameters.get("id"))
                            .getCreatedAt());
                    audit.setModifiedBy("Modifying user");
                    ResponseEntity resultObject = (ResponseEntity) joinPoint.proceed();
                    audit.setModifiedAt(new Timestamp(System.currentTimeMillis()));
                    audit.setNewEntityJson(Objects.requireNonNullElse(resultObject.getBody(), "DELETED").toString());
                    audit.setEntityJson(Objects.requireNonNullElse(resultObject.getBody(), "DELETED").toString());
                }
            }
            audit = auditRepository.save(audit);
            log.info("Аудит с id - \"{}\" сохранен в базе данных", audit.getId());
        } catch (Exception e) {
            log.error(String.format("Аудит не сохранен в базе данных из-за ошибки: {}" + e.getClass()));
            throw e;
        }
    }

    /**
     * Метод для получения параметров из JoinPoint-a
     **/
    private Map<String, Object> getParametersFromJoinPoint(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Map<String, Object> parametersFromMethod = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            parametersFromMethod.put(parameterNames[i], args[i]);
        }
        return parametersFromMethod;
    }

    /**
     * Метод для нахождения ранее созданного аудита entity по id этого entity
     **/
    public AuditEntity getCreatedAuditFromEntityId(Auditable auditable, Long id) {
        String previousJson = serviceHashMap.get(auditable.entityType()).findById(id).toString();
        return auditRepository.findByEntityJson(previousJson);
    }
}





