package com.bank.antifraud.repository.suspicioustransfer;
import com.bank.antifraud.entity.card.SuspiciousCardTransfer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SuspiciousCardTransferRepository extends BaseSuspiciousRepository<SuspiciousCardTransfer> {

    @Override
    @Query("from SuspiciousCardTransfer c where c.transferId = :transferId")
    public SuspiciousCardTransfer findByTransferId(@Param("transferId") long transferId);
}
