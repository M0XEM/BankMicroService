package com.bank.antifraud.service;

import com.bank.antifraud.entity.Audit;
import com.bank.antifraud.repository.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AuditService {
    private final AuditRepository auditRepository;

    @Autowired
    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Transactional
    public void createAudit(Audit audit) {
        auditRepository.save(audit);
    }

    public List<Audit> readAll() {
        return auditRepository.findAll();
    }
}
