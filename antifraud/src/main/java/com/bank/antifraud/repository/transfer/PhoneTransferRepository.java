package com.bank.antifraud.repository.transfer;

import com.bank.antifraud.entity.card.CardTransfer;
import com.bank.antifraud.entity.phone.PhoneTransfer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneTransferRepository extends BaseTransferRepository<PhoneTransfer> {

    @Override
    @Query("from PhoneTransfer p where p.number = :number")
    PhoneTransfer findByNumber(@Param("number") long number);
}