package com.bank.antifraud.entity.account;


import com.bank.antifraud.entity.abstractclasses.Transfer;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

//@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "account_transfer", schema = "transfer")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class AccountTransfer extends Transfer {

    @Column(name = "account_number")
    private long number;

}
