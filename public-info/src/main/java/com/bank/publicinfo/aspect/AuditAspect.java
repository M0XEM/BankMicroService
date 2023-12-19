package com.bank.publicinfo.aspect;

import com.bank.publicinfo.aspect.annotation.Auditable;
import com.bank.publicinfo.entity.AuditEntity;
import com.bank.publicinfo.repository.AuditRepository;
import com.bank.publicinfo.service.interfaces.AtmService;
import com.bank.publicinfo.service.interfaces.BankDetailsService;
import com.bank.publicinfo.service.interfaces.BranchService;
import com.bank.publicinfo.service.interfaces.CertificateService;
import com.bank.publicinfo.service.interfaces.LicenseService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
@RequiredArgsConstructor
public class AuditAspect {
    private final AuditRepository auditRepository;
    private final AtmService atmService;
    private final BankDetailsService bankDetailsService;
    private final BranchService branchService;
    private final CertificateService certificateService;
    private final LicenseService licenseService;

    @Around(value = "@annotation(auditable)", argNames = "joinPoint, auditable")
    public void aroundAllAuditableMethods(ProceedingJoinPoint joinPoint, Auditable auditable) throws Throwable {
        Map<String, Object> methodParameters = getParametersFromJoinPoint(joinPoint);
        try {
            switch (auditable.operationType()) {
                case "save" -> {
                    Object resultObject = joinPoint.proceed();
                    AuditEntity audit = AuditEntity.builder()
                            .entityType(auditable.entityType())
                            .operationType("save")
                            .createdBy("user")
                            .modifiedBy(null)
                            .createdAt(new Timestamp(System.currentTimeMillis()))
                            .modifiedAt(null)
                            .newEntityJson(null)
                            .entityJson(resultObject.toString())
                            .build();
                    auditRepository.save(audit);
                }
                case "delete" -> {
                    AuditEntity audit = AuditEntity.builder()
                            .entityType(auditable.entityType())
                            .operationType("delete")
                            .createdBy(auditRepository
                                    .findByEntityJson(getPreviousJsonFromId(auditable, (Long) methodParameters.get("id")))
                                    .getCreatedBy())
                            .modifiedBy("user")
                            .createdAt(auditRepository
                                    .findByEntityJson(getPreviousJsonFromId(auditable, (Long) methodParameters.get("id")))
                                    .getCreatedAt())
                            .modifiedAt(new Timestamp(System.currentTimeMillis()))
                            .newEntityJson("DELETED")
                            .entityJson("DELETED")
                            .build();
                    auditRepository.save(audit);
                    joinPoint.proceed();
                }
                case "update" -> {
                    AuditEntity audit = AuditEntity.builder()
                            .entityType(auditable.entityType())
                            .operationType("update")
                            .createdBy(auditRepository
                                    .findByEntityJson(getPreviousJsonFromId(auditable, (Long) methodParameters.get("id")))
                                    .getCreatedBy()
                            )
                            .modifiedBy("user")
                            .createdAt(auditRepository
                                    .findByEntityJson(getPreviousJsonFromId(auditable, (Long) methodParameters.get("id")))
                                    .getCreatedAt()
                            )
                            .modifiedAt(new Timestamp(System.currentTimeMillis()))
                            .newEntityJson(null)
                            .entityJson(null)
                            .build();
                    Object resultObject = joinPoint.proceed();
                    audit.setNewEntityJson(resultObject.toString());
                    audit.setEntityJson(resultObject.toString());
                    auditRepository.save(audit);
                }
            }
        } catch (Exception e) {
            AuditEntity audit = AuditEntity.builder()
                    .entityType(auditable.entityType())
                    .operationType("FAILURE")
                    .createdBy("user")
                    .modifiedBy(null)
                    .createdAt(new Timestamp(System.currentTimeMillis()))
                    .modifiedAt(null)
                    .newEntityJson(null)
                    .entityJson("Trying: operationType = " + auditable.operationType() + ", methodParameters = " + methodParameters)
                    .build();
            auditRepository.save(audit);
            throw e;
        }
    }

    /**
     * Метод для создания map из аргументов метода
     * Нужен, чтобы находить id из начальных параметров метода
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
     * Метод для нахождения json-а первого созданного entity по его id
     **/
    private String getPreviousJsonFromId(Auditable auditable, Long id) {
        String previousJson;
        previousJson = switch (auditable.entityType()) {
            case "atm" -> atmService.findById(id).toString();
            case "branch" -> branchService.findById(id).toString();
            case "bankDetails" -> bankDetailsService.findById(id).toString();
            case "license" -> licenseService.findById(id).toString();
            case "certificate" -> certificateService.findById(id).toString();
            default -> "ERROR: no id";
        };
        return previousJson;
    }
}





