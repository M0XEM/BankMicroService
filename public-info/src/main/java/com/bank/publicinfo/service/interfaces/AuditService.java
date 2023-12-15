package com.bank.publicinfo.service.interfaces;

import com.bank.publicinfo.dto.AuditDto;

import java.util.List;

public interface AuditService {
    AuditDto findById(Long id);

    List<AuditDto> findAll();

    AuditDto save(AuditDto auditDto);

    AuditDto update(Long id, AuditDto auditDto);

    void deleteById(Long id);

}
