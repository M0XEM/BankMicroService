package com.bank.publicinfo.service.classes;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entity.CertificateEntity;
import com.bank.publicinfo.exception.BadRequestException;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.mapper.CertificateMapper;
import com.bank.publicinfo.repository.CertificateRepository;
import com.bank.publicinfo.service.interfaces.CertificateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;

    @Override
    @Transactional(readOnly = true)
    public CertificateDto findById(Long id) {
        return certificateMapper.toDto(certificateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Нет сертификата с таким id - " + id)));
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
    public CertificateDto save(CertificateDto certificateDto) {
        if (certificateDto == null) {
            throw new BadRequestException("В запросе нет данных о сертификате банка");
        }
        CertificateEntity entity = certificateRepository.save(certificateMapper.toEntity(certificateDto));
        log.info("Сертификат с id - \"{}\" для банка с id - \"{}\" сохранен в базе данных",
                entity.getId(), entity.getBankDetails().getId());
        return certificateMapper.toDto(entity);
    }

    @Override
    public CertificateDto update(Long id, CertificateDto certificateDto) {
        if (certificateDto == null) {
            throw new BadRequestException("В запросе нет данных о сертификате банка");
        }
        certificateDto.setId(id);
        CertificateEntity entity = certificateRepository.save(certificateMapper.toEntity(certificateDto));
        log.info("Сертификат с id - \"{}\" для банка с id - \"{}\" обновлен в базе данных",
                entity.getId(), entity.getBankDetails().getId());
        return certificateMapper.toDto(entity);
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
