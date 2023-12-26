package com.bank.antifraud.repository.transfer;

import com.bank.antifraud.entity.abstractclasses.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseTransferRepository<T extends Transfer> extends JpaRepository<T, Long> {
    T findByNumber(long transferId);
}
