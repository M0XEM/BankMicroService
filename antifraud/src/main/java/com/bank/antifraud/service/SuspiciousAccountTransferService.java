package com.bank.antifraud.service;

import com.bank.antifraud.entity.account.AccountTransfer;
import com.bank.antifraud.entity.account.SuspiciousAccountTransfer;
import com.bank.antifraud.repository.suspicioustransfer.BaseSuspiciousRepository;
import com.bank.antifraud.repository.suspicioustransfer.SuspiciousAccountTransferRepository;
import com.bank.antifraud.repository.transfer.AccountTransferRepository;
import com.bank.antifraud.repository.transfer.BaseTransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SuspiciousAccountTransferService extends BaseServiceImpl<SuspiciousAccountTransfer, AccountTransfer>{

    private final SuspiciousAccountTransferRepository suspiciousAccountTransferRepository;
    private final AccountTransferRepository accountTransferRepository;

    @Autowired
    public SuspiciousAccountTransferService(SuspiciousAccountTransferRepository suspiciousAccountTransferRepository, AccountTransferRepository accountTransferRepository) {
        this.suspiciousAccountTransferRepository = suspiciousAccountTransferRepository;
        this.accountTransferRepository = accountTransferRepository;
    }

    @Override
    SuspiciousAccountTransfer getEntity() {
        return new SuspiciousAccountTransfer();
    }

    @Override
    BaseSuspiciousRepository<SuspiciousAccountTransfer> getSuspiciousRepository() {
        return suspiciousAccountTransferRepository;
    }

    @Override
    BaseTransferRepository<AccountTransfer> getTransferRepository() {
        return accountTransferRepository;
    }

}
