package com.bank.antifraud.entity.account;


import com.bank.antifraud.entity.abstractclasses.Transfer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "account_transfer", schema = "transfer")
@Data
@NoArgsConstructor
public class AccountTransfer extends Transfer {

    @Column(name = "account_number")
    private long number;

}
