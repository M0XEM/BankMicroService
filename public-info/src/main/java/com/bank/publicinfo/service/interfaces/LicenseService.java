package com.bank.publicinfo.service.interfaces;

import com.bank.publicinfo.dto.LicenseDto;

import java.util.List;

public interface LicenseService extends MainService<LicenseDto> {
    List<LicenseDto> findAllByBankDetailsId(Long bankDetailsId);

    void deleteByLicenseIdAndBankDetailsId(Long licenseId, Long bankDetailsId);
}
