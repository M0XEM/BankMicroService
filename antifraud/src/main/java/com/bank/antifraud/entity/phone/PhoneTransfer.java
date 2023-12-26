package com.bank.antifraud.entity.phone;

import com.bank.antifraud.entity.abstractclasses.Transfer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "phone_transfer", schema = "transfer")
@Data
@NoArgsConstructor
public class PhoneTransfer extends Transfer {

    @Column(name = "phone_number")
    private long number;

}
