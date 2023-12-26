package com.bank.publicinfo.service.interfaces;

import com.bank.publicinfo.dto.CertificateDto;

import java.util.List;

public interface CertificateService extends MainService<CertificateDto> {
    List<CertificateDto> findAllByBankDetailsId(Long bankDetailsId);

    void deleteByCertificateIdAndBankDetailsId(Long certificateId, Long bankDetailsId);
}
