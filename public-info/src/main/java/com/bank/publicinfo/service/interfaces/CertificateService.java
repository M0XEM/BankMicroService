package com.bank.publicinfo.service.interfaces;

import com.bank.publicinfo.dto.CertificateDto;

import java.util.List;

public interface CertificateService {
    CertificateDto save(CertificateDto certificateDto);

    CertificateDto findById(Long id);

    void deleteByCertificateIdAndBankDetailsId(Long certificateId, Long bankDetailsId);

    List<CertificateDto> findAll();

    List<CertificateDto> findAllByBankDetailsId(Long bankDetailsId);
}
