package com.bank.antifraud.repository.suspicioustransfer;
import com.bank.antifraud.entity.account.SuspiciousAccountTransfer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SuspiciousAccountTransferRepository extends BaseSuspiciousRepository<SuspiciousAccountTransfer> {

    @Override
    @Query("from SuspiciousAccountTransfer a where a.transferId = :transferId")
    SuspiciousAccountTransfer findByTransferId(@Param("transferId") long transferId);
}
