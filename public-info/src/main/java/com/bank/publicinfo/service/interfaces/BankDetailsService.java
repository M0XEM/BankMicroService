package com.bank.publicinfo.service.interfaces;

import com.bank.publicinfo.dto.BankDetailsDto;

import java.util.List;

public interface BankDetailsService {
    BankDetailsDto save(BankDetailsDto bankDetailsDto);

    BankDetailsDto findById(Long id);

    BankDetailsDto findByBik(Long bik);

    BankDetailsDto findByInn(Long inn);

    BankDetailsDto findByKpp(Long kpp);

    BankDetailsDto findByCorAccount(Integer corAccount);

    void deleteById(Long id);

    List<BankDetailsDto> findAll();
}
