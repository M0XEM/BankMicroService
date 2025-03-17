package com.bank.account.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "account_details", schema = "account")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetails {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "passport_id", nullable = false)
    private Long passportId;

    @Column(name = "account_number", nullable = false)
    private Long accountNumber;

    @Column(name = "bank_details_id", nullable = false)
    private Long bankDetailsId;

    @Column(name = "money", precision = 20, scale = 2, nullable = false)
    private BigDecimal money;

    @Column(name = "negative_balance", nullable = false)
    private Boolean negativeBalance;

    @Column(name = "profile_id", nullable = false)
    private Long profileId;
}
