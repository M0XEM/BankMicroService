package com.bank.publicinfo.service.classes;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entity.BankDetailsEntity;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.mapper.BankDetailsMapper;
import com.bank.publicinfo.repository.BankDetailsRepository;
import com.bank.publicinfo.service.interfaces.BankDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class BankDetailsServiceImpl extends MainServiceClass<BankDetailsDto, BankDetailsEntity> implements BankDetailsService {
    private final BankDetailsRepository bankDetailsRepository;
    private final BankDetailsMapper bankDetailsMapper;

    public BankDetailsServiceImpl(BankDetailsRepository bankDetailsRepository, BankDetailsMapper bankDetailsMapper) {
        super(bankDetailsMapper, bankDetailsRepository, BankDetailsEntity.class);
        this.bankDetailsRepository = bankDetailsRepository;
        this.bankDetailsMapper = bankDetailsMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public BankDetailsDto findByBik(Long bik) {
        return bankDetailsMapper.toDto(bankDetailsRepository.findByBik(bik)
                .orElseThrow(() -> new NotFoundException("Нет банка с таким bik - " + bik)));
    }

    @Override
    @Transactional(readOnly = true)
    public BankDetailsDto findByInn(Long inn) {
        return bankDetailsMapper.toDto(bankDetailsRepository.findByInn(inn)
                .orElseThrow(() -> new NotFoundException("Нет банка с таким ИНН - " + inn)));
    }

    @Override
    @Transactional(readOnly = true)
    public BankDetailsDto findByKpp(Long kpp) {
        return bankDetailsMapper.toDto(bankDetailsRepository.findByKpp(kpp)
                .orElseThrow(() -> new NotFoundException("Нет банка с таким КПП - " + kpp)));
    }

    @Override
    @Transactional(readOnly = true)
    public BankDetailsDto findByCorAccount(Integer corAccount) {
        return bankDetailsMapper.toDto(bankDetailsRepository.findByCorAccount(corAccount)
                .orElseThrow(() -> new NotFoundException("Нет банка с таким корр. счётом - " + corAccount)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankDetailsDto> findAll() {
        return bankDetailsMapper.toDtoList(bankDetailsRepository.findAll());
    }
}
