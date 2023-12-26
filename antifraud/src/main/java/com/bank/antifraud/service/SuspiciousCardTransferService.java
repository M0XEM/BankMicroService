package com.bank.antifraud.service;

import com.bank.antifraud.entity.card.CardTransfer;
import com.bank.antifraud.entity.card.SuspiciousCardTransfer;
import com.bank.antifraud.repository.suspicioustransfer.BaseSuspiciousRepository;
import com.bank.antifraud.repository.suspicioustransfer.SuspiciousCardTransferRepository;
import com.bank.antifraud.repository.transfer.BaseTransferRepository;
import com.bank.antifraud.repository.transfer.CardTransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SuspiciousCardTransferService extends BaseServiceImpl<SuspiciousCardTransfer, CardTransfer>{

    private final SuspiciousCardTransferRepository suspiciousCardTransferRepository;
    private final CardTransferRepository cardTransferRepository;

    @Autowired
    public SuspiciousCardTransferService(SuspiciousCardTransferRepository suspiciousCardTransferRepository, CardTransferRepository cardTransferRepository) {
        this.suspiciousCardTransferRepository = suspiciousCardTransferRepository;
        this.cardTransferRepository = cardTransferRepository;
    }

    @Override
    SuspiciousCardTransfer getEntity() {
        return new SuspiciousCardTransfer();
    }

    @Override
    BaseSuspiciousRepository<SuspiciousCardTransfer> getSuspiciousRepository() {
        return suspiciousCardTransferRepository;
    }

    @Override
    BaseTransferRepository<CardTransfer> getTransferRepository() {
        return cardTransferRepository;
    }

}
