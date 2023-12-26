package com.bank.publicinfo.service.classes;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entity.CertificateEntity;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.mapper.CertificateMapper;
import com.bank.publicinfo.repository.CertificateRepository;
import com.bank.publicinfo.service.interfaces.CertificateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class CertificateServiceImpl extends MainServiceClass<CertificateDto, CertificateEntity> implements CertificateService {
    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;

    public CertificateServiceImpl(CertificateRepository certificateRepository, CertificateMapper certificateMapper) {
        super(certificateMapper, certificateRepository, CertificateEntity.class);
        this.certificateRepository = certificateRepository;
        this.certificateMapper = certificateMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CertificateDto> findAllByBankDetailsId(Long bankDetailsId) {
        return certificateMapper.toDtoList(certificateRepository.findAllByBankDetails_Id(bankDetailsId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CertificateDto> findAll() {
        return certificateMapper.toDtoList(certificateRepository.findAll());
    }

    @Override
    public void deleteByCertificateIdAndBankDetailsId(Long certificateId, Long bankDetailsId) {
        try {
            certificateRepository.deleteByIdAndBankDetails_Id(certificateId, bankDetailsId);
            log.info("Сертификат с id - \"{}\" для банка с id - \"{}\" удален из базы данных", certificateId, bankDetailsId);
        } catch (Exception e) {
            log.error("Сертификата с id - \"{}\" для банка с id - \"{}\" в базе данных не существует", certificateId, bankDetailsId);
            throw new NotFoundException("Сертификата с заданными параметрами не существует");
        }
    }
}
