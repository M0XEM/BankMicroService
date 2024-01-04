package com.bank.antifraud.entity.card;

import com.bank.antifraud.entity.abstractclasses.Transfer;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "card_transfer", schema = "transfer")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class CardTransfer extends Transfer {

    @Column(name = "card_number")
    private long number;

}
