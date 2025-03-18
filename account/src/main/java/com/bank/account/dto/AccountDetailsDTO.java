package com.bank.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetailsDTO {
    private Long id;
    private Long passportId;
    private Long accountNumber;
    private BigDecimal money;
    private Boolean negativeBalance;
    private Long profileId;
    private Long bankDetailsId;
}