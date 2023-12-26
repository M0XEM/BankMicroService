package com.bank.antifraud.entity.account;

import com.bank.antifraud.entity.abstractclasses.SuspiciousTransfer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "suspicious_account_transfers", schema = "anti_fraud")
@Data
@NoArgsConstructor
public class SuspiciousAccountTransfer extends SuspiciousTransfer {

    @Column(name = "account_transfer_id", unique = true, nullable = false)
    private long transferId;

}
