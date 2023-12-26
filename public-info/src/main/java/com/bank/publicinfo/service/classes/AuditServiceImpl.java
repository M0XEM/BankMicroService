package com.bank.publicinfo.service.classes;

import com.bank.publicinfo.dto.AuditDto;
import com.bank.publicinfo.entity.AuditEntity;
import com.bank.publicinfo.mapper.AuditMapper;
import com.bank.publicinfo.repository.AuditRepository;
import com.bank.publicinfo.service.interfaces.AuditService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class AuditServiceImpl extends MainServiceClass<AuditDto, AuditEntity> implements AuditService {
    private final AuditRepository auditRepository;
    private final AuditMapper auditMapper;

    public AuditServiceImpl(AuditRepository auditRepository, AuditMapper auditMapper) {
        super(auditMapper, auditRepository, AuditEntity.class);
        this.auditRepository = auditRepository;
        this.auditMapper = auditMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditDto> findAll() {
        return auditMapper.toDtoList(auditRepository.findAll());
    }
}
