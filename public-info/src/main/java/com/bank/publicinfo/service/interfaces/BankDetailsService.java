package com.bank.publicinfo.service.interfaces;

import com.bank.publicinfo.dto.BankDetailsDto;

public interface BankDetailsService extends MainService<BankDetailsDto> {
    BankDetailsDto findByBik(Long bik);

    BankDetailsDto findByInn(Long inn);

    BankDetailsDto findByKpp(Long kpp);

    BankDetailsDto findByCorAccount(Integer corAccount);
}
