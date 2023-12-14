package com.bank.publicinfo.service.interfaces;

import com.bank.publicinfo.dto.BranchDto;

import java.util.List;

public interface BranchService {
    BranchDto save(BranchDto branchDto);

    BranchDto findById(Long id);

    BranchDto findByPhoneNumber(Long phoneNumber);

    void deleteById(Long id);

    List<BranchDto> findAll();
}
