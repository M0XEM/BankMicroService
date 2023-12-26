package com.bank.publicinfo.service.interfaces;

import com.bank.publicinfo.dto.AtmDto;

import java.util.List;

public interface AtmService extends MainService<AtmDto> {
    List<AtmDto> findAllByBranchId(Long branchId);
}
