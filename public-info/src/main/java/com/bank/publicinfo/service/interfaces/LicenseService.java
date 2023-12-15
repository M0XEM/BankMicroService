package com.bank.publicinfo.service.interfaces;

import com.bank.publicinfo.dto.LicenseDto;

import java.util.List;

public interface LicenseService {
    LicenseDto findById(Long id);

    List<LicenseDto> findAll();

    List<LicenseDto> findAllByBankDetailsId(Long bankDetailsId);

    LicenseDto save(LicenseDto certificateDto);

    void deleteByLicenseIdAndBankDetailsId(Long licenseId, Long bankDetailsId);

    LicenseDto update(Long id, LicenseDto licenseDto);
}
