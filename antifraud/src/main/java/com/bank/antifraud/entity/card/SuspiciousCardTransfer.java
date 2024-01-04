package com.bank.antifraud.entity.card;

import com.bank.antifraud.entity.abstractclasses.SuspiciousTransfer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "suspicious_card_transfer", schema = "anti_fraud")
@Data
@NoArgsConstructor
@SuperBuilder
public class SuspiciousCardTransfer extends SuspiciousTransfer {

    @Column(name = "card_transfer_id", unique = true, nullable = false)
    private long transferId;

}
