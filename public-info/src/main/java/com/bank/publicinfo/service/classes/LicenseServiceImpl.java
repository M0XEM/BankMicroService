package com.bank.publicinfo.service.classes;

import com.bank.publicinfo.aspect.annotation.Auditable;
import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.LicenseEntity;
import com.bank.publicinfo.exception.BadRequestException;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.mapper.LicenseMapper;
import com.bank.publicinfo.repository.LicenseRepository;
import com.bank.publicinfo.service.interfaces.LicenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LicenseServiceImpl implements LicenseService {
    private final LicenseRepository licenseRepository;
    private final LicenseMapper licenseMapper;

    @Override
    @Transactional(readOnly = true)
    public LicenseDto findById(Long id) {
        return licenseMapper.toDto(licenseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Нет лицензии с таким id - " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LicenseDto> findAllByBankDetailsId(Long bankDetailsId) {
        return licenseMapper.toDtoList(licenseRepository.findAllByBankDetails_Id(bankDetailsId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LicenseDto> findAll() {
        return licenseMapper.toDtoList(licenseRepository.findAll());
    }

    @Override
    @Auditable(entityType = "license", operationType = "save")
    public LicenseDto save(LicenseDto dto) {
        if (dto == null) {
            throw new BadRequestException("В запросе нет данных о лицензии банка");
        }
        LicenseEntity entity = licenseRepository.save(licenseMapper.toEntity(dto));
        log.info("Лицензия с id - \"{}\" для банка с id - \"{}\" сохранен в базе данных",
                entity.getId(), entity.getBankDetails().getId());
        return licenseMapper.toDto(entity);
    }

    @Override
    @Auditable(entityType = "license", operationType = "update")
    public LicenseDto update(Long id, LicenseDto dto) {
        if (dto == null) {
            throw new BadRequestException("В запросе нет данных о лицензии банка");
        }
        dto.setId(id);
        LicenseEntity entity = licenseRepository.save(licenseMapper.toEntity(dto));
        log.info("Лицензия с id - \"{}\" для банка с id - \"{}\" обновлена в базе данных",
                entity.getId(), entity.getBankDetails().getId());
        return licenseMapper.toDto(entity);
    }

    @Override
    @Auditable(entityType = "license", operationType = "delete")
    public void deleteByLicenseIdAndBankDetailsId(Long licenseId, Long bankDetailsId) {
        try {
            licenseRepository.deleteByIdAndBankDetails_Id(licenseId, bankDetailsId);
            log.info("Лицензия с id - \"{}\" для банка с id - \"{}\" удалена из базы данных", licenseId, bankDetailsId);
        } catch (Exception e) {
            log.error("Лицензии с id - \"{}\" для банка с id - \"{}\" в базе данных не существует", licenseId, bankDetailsId);
            throw new NotFoundException("Лицензии с заданными параметрами не существует");
        }
    }
}
