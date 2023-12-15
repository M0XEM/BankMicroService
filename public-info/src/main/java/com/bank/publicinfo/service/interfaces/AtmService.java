package com.bank.publicinfo.service.interfaces;

import com.bank.publicinfo.dto.AtmDto;

import java.util.List;

public interface AtmService {
    AtmDto findById(Long id);

    List<AtmDto> findAllByBranchId(Long branchId);

    List<AtmDto> findAll();

    AtmDto save(AtmDto atmDto);

    AtmDto update(Long id, AtmDto atmDto);

    void deleteByAtmId(Long atmId);

    void deleteByAtmIdAndBranchId(Long atmId, Long branchId);
}
