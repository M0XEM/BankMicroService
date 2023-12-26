package com.bank.publicinfo.service.interfaces;

import com.bank.publicinfo.dto.BranchDto;

public interface BranchService extends MainService<BranchDto> {
    BranchDto findByPhoneNumber(Long phoneNumber);
}
