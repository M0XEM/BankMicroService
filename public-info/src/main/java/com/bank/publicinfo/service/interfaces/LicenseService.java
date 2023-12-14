package com.bank.publicinfo.service.interfaces;

import com.bank.publicinfo.dto.LicenseDto;

import java.util.List;

public interface LicenseService {
    LicenseDto save(LicenseDto certificateDto);

    LicenseDto findById(Long id);

    void deleteByLicenseIdAndBankDetailsId(Long licenseId, Long bankDetailsId);

    List<LicenseDto> findAll();

    List<LicenseDto> findAllByBankDetailsId(Long bankDetailsId);
}
