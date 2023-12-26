package com.bank.antifraud.entity.abstractclasses;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@MappedSuperclass
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Transient
    private long number;

    @Column(name = "amount")
    private double amount;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "account_details_id")
    private long accountDetailsId;

}
