package com.bank.antifraud.repository.transfer;

import com.bank.antifraud.entity.account.AccountTransfer;
import com.bank.antifraud.entity.card.CardTransfer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CardTransferRepository extends BaseTransferRepository<CardTransfer> {

    @Override
    @Query("from CardTransfer c where c.number = :number")
    CardTransfer findByNumber(@Param("number") long number);
}