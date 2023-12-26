package com.bank.antifraud.repository.suspicioustransfer;
import com.bank.antifraud.entity.phone.SuspiciousPhoneTransfer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SuspiciousPhoneTransferRepository extends BaseSuspiciousRepository<SuspiciousPhoneTransfer> {

    @Override
    @Query("from SuspiciousPhoneTransfer p where p.transferId= :transferId")
    public SuspiciousPhoneTransfer findByTransferId(@Param("transferId") long transferId);
}
