package com.bank.history.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class HistoryDto {

    private Long id;

    private Long transferAuditId;

    private Long profileAuditId;

    private Long accountAuditId;

    private Long antiFraudAuditId;

    private Long publicBankInfoAuditId;

    private Long authorizationAuditId;

}
