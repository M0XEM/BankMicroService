package com.bank.antifraud.repository.suspicioustransfer;

import com.bank.antifraud.entity.abstractclasses.SuspiciousTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseSuspiciousRepository<T extends SuspiciousTransfer> extends JpaRepository<T, Long> {
    T findByTransferId(long transferId);
}