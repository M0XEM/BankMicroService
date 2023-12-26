package com.bank.antifraud.service;

import com.bank.antifraud.entity.phone.PhoneTransfer;
import com.bank.antifraud.entity.phone.SuspiciousPhoneTransfer;
import com.bank.antifraud.repository.suspicioustransfer.BaseSuspiciousRepository;
import com.bank.antifraud.repository.suspicioustransfer.SuspiciousPhoneTransferRepository;
import com.bank.antifraud.repository.transfer.BaseTransferRepository;
import com.bank.antifraud.repository.transfer.PhoneTransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SuspiciousPhoneTransferService extends BaseServiceImpl<SuspiciousPhoneTransfer, PhoneTransfer>{

    private final SuspiciousPhoneTransferRepository suspiciousPhoneTransferRepository;
    private final PhoneTransferRepository phoneTransferRepository;

    @Autowired
    public SuspiciousPhoneTransferService(SuspiciousPhoneTransferRepository suspiciousPhoneTransferRepository, PhoneTransferRepository phoneTransferRepository) {
        this.suspiciousPhoneTransferRepository = suspiciousPhoneTransferRepository;
        this.phoneTransferRepository = phoneTransferRepository;
    }

    @Override
    SuspiciousPhoneTransfer getEntity() {
        return new SuspiciousPhoneTransfer();
    }

    @Override
    BaseSuspiciousRepository<SuspiciousPhoneTransfer> getSuspiciousRepository() {
        return suspiciousPhoneTransferRepository;
    }

    @Override
    BaseTransferRepository<PhoneTransfer> getTransferRepository() {
        return phoneTransferRepository;
    }

}
