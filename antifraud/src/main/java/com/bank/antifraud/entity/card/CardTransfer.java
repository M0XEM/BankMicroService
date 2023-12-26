package com.bank.antifraud.entity.card;

import com.bank.antifraud.entity.abstractclasses.Transfer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "card_transfer", schema = "transfer")
@Data
@NoArgsConstructor
public class CardTransfer extends Transfer {

    @Column(name = "card_number")
    private long number;

}
