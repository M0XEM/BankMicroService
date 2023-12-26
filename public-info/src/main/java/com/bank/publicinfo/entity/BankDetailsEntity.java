package com.bank.publicinfo.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bank_details", schema = "public_bank_information")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BankDetailsEntity implements MainEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "bik", unique = true, nullable = false)
    Long bik;

    @Column(name = "inn", unique = true, nullable = false)
    Long inn;

    @Column(name = "kpp", unique = true, nullable = false)
    Long kpp;

    @Column(name = "cor_account", unique = true, nullable = false)
    Integer corAccount;

    @Column(name = "city", length = 180, nullable = false)
    String city;

    @Column(name = "joint_stock_company", length = 15, nullable = false)
    String jointStockCompany;

    @Column(name = "name", length = 80, nullable = false)
    String name;
}
