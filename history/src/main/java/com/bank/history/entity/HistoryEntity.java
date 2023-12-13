package com.bank.history.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "history", schema = "history")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class HistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transfer_audit_id")
    private Long transferAuditId;

    @Column(name = "profile_audit_id")
    private Long profileAuditId;

    @Column(name = "account_audit_id")
    private Long accountAuditId;

    @Column(name = "anti_fraud_audit_id")
    private Long antiFraudAuditId;

    @Column(name = "public_bank_info_audit_id")
    private Long publicBankInfoAuditId;

    @Column(name = "authorization_audit_id")
    private Long authorizationAuditId;

}
