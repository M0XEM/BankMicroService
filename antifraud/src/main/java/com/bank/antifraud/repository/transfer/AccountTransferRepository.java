package com.bank.antifraud.repository.transfer;

import com.bank.antifraud.entity.account.AccountTransfer;
import com.bank.antifraud.entity.account.SuspiciousAccountTransfer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTransferRepository extends BaseTransferRepository<AccountTransfer> {

    @Override
    @Query("from AccountTransfer a where a.number = :number")
    AccountTransfer findByNumber(@Param("number") long number);
}
