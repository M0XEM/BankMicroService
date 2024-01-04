package com.bank.antifraud.entity.account;

import com.bank.antifraud.entity.abstractclasses.SuspiciousTransfer;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "suspicious_account_transfers", schema = "anti_fraud")
@SuperBuilder
@NoArgsConstructor
public class SuspiciousAccountTransfer extends SuspiciousTransfer {

    @Column(name = "account_transfer_id", unique = true, nullable = false)
    private long transferId;

    @Override
    public String toString() {
        return super.toString();
    }
}
