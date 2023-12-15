package com.bank.publicinfo.service.interfaces;

import com.bank.publicinfo.dto.CertificateDto;

import java.util.List;

public interface CertificateService {
    CertificateDto findById(Long id);

    List<CertificateDto> findAllByBankDetailsId(Long bankDetailsId);

    List<CertificateDto> findAll();

    CertificateDto save(CertificateDto certificateDto);

    CertificateDto update(Long id, CertificateDto certificateDto);

    void deleteByCertificateIdAndBankDetailsId(Long certificateId, Long bankDetailsId);
}
