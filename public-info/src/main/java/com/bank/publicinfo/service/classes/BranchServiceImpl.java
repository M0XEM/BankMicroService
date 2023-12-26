package com.bank.publicinfo.service.classes;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.BranchEntity;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.mapper.BranchMapper;
import com.bank.publicinfo.repository.BranchRepository;
import com.bank.publicinfo.service.interfaces.BranchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class BranchServiceImpl extends MainServiceClass<BranchDto, BranchEntity> implements BranchService {
    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;

    public BranchServiceImpl(BranchRepository branchRepository, BranchMapper branchMapper) {
        super(branchMapper, branchRepository, BranchEntity.class);
        this.branchRepository = branchRepository;
        this.branchMapper = branchMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public BranchDto findByPhoneNumber(Long phoneNumber) {
        return branchMapper.toDto(branchRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("Нет отделения банка с таким номером телефона - " + phoneNumber)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BranchDto> findAll() {
        return branchMapper.toDtoList(branchRepository.findAll());
    }
}
