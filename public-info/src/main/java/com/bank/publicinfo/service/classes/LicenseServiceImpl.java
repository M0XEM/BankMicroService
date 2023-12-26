package com.bank.publicinfo.service.classes;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.LicenseEntity;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.mapper.LicenseMapper;
import com.bank.publicinfo.repository.LicenseRepository;
import com.bank.publicinfo.service.interfaces.LicenseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class LicenseServiceImpl extends MainServiceClass<LicenseDto, LicenseEntity> implements LicenseService {
    private final LicenseRepository licenseRepository;
    private final LicenseMapper licenseMapper;

    public LicenseServiceImpl(LicenseRepository licenseRepository, LicenseMapper licenseMapper) {
        super(licenseMapper, licenseRepository, LicenseEntity.class);
        this.licenseRepository = licenseRepository;
        this.licenseMapper = licenseMapper;
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
