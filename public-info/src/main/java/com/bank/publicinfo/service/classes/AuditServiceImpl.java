package com.bank.publicinfo.service.classes;

import com.bank.publicinfo.dto.AuditDto;
import com.bank.publicinfo.entity.AuditEntity;
import com.bank.publicinfo.exception.BadRequestException;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.mapper.AuditMapper;
import com.bank.publicinfo.repository.AuditRepository;
import com.bank.publicinfo.service.interfaces.AuditService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Builder
@Transactional
public class AuditServiceImpl implements AuditService {
    private final AuditRepository auditRepository;
    private final AuditMapper auditMapper;

    @Override
    @Transactional(readOnly = true)
    public AuditDto findById(Long id) {
        return auditMapper.toDto(auditRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Нет аудита с таким id - " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditDto> findAll() {
        return auditMapper.toDtoList(auditRepository.findAll());
    }

    @Override
    public AuditDto save(AuditDto dto) {
        if (dto == null) {
            throw new BadRequestException("В запросе нет данных об аудите");
        }
        AuditEntity entity = auditRepository.save(auditMapper.toEntity(dto));
        log.info("Аудит с id - \"{}\" сохранен в базе данных", entity.getId());
        return auditMapper.toDto(entity);
    }

    @Override
    public AuditDto update(Long id, AuditDto dto) {
        if (dto == null) {
            throw new BadRequestException("В запросе нет данных об аудите");
        }
        dto.setId(id);
        AuditEntity entity = auditRepository.save(auditMapper.toEntity(dto));
        log.info("Аудит с id - \"{}\" обновлен в базе данных", entity.getId());
        return auditMapper.toDto(entity);
    }

    @Override
    public void deleteById(Long id) {
        try {
            auditRepository.deleteById(id);
            log.info("Аудит с id - \"{}\" удален из базы данных", id);
        } catch (Exception e) {
            log.error("Аудита с id - \"{}\" в базе данных не существует", id);
            throw new NotFoundException("Аудита с заданным id не существует");
        }
    }
}
