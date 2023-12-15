package com.bank.publicinfo.service.interfaces;

import com.bank.publicinfo.dto.BankDetailsDto;

import java.util.List;

public interface BankDetailsService {
    BankDetailsDto findById(Long id);

    BankDetailsDto findByBik(Long bik);

    BankDetailsDto findByInn(Long inn);

    BankDetailsDto findByKpp(Long kpp);

    List<BankDetailsDto> findAll();

    BankDetailsDto findByCorAccount(Integer corAccount);

    BankDetailsDto save(BankDetailsDto bankDetailsDto);

    BankDetailsDto update(Long id, BankDetailsDto bankDetailsDto);

    void deleteById(Long id);
}
