package com.bank.publicinfo.service.interfaces;

import com.bank.publicinfo.dto.AuditDto;

import java.util.List;

public interface AuditService {
    AuditDto save(AuditDto auditDto);

    AuditDto findById(Long id);

    void deleteById(Long id);

    List<AuditDto> findAll();
}
