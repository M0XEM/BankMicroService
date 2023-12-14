package com.bank.publicinfo.service.interfaces;

import com.bank.publicinfo.dto.AtmDto;

import java.util.List;

public interface AtmService {
    AtmDto save(AtmDto atmDto);

    AtmDto findById(Long id);

    void deleteByAtmIdAndBranchId(Long atmId, Long branchId);

    List<AtmDto> findAll();

    List<AtmDto> findAllByBranchId(Long branchId);
}
