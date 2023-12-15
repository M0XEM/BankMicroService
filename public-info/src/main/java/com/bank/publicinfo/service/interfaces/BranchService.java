package com.bank.publicinfo.service.interfaces;

import com.bank.publicinfo.dto.BranchDto;

import java.util.List;

public interface BranchService {
    BranchDto findById(Long id);

    BranchDto findByPhoneNumber(Long phoneNumber);

    List<BranchDto> findAll();

    BranchDto save(BranchDto branchDto);

    BranchDto update(Long id, BranchDto branchDto);

    void deleteById(Long id);
}
