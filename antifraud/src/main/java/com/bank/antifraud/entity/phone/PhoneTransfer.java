package com.bank.antifraud.entity.phone;

import com.bank.antifraud.entity.abstractclasses.Transfer;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "phone_transfer", schema = "transfer")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class PhoneTransfer extends Transfer {

    @Column(name = "phone_number")
    private long number;

}
