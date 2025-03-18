package com.bank.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailsCreateRequest {

    @NotNull
    private Long passportId;

    private Long bankDetailsId;

    @NotNull
    private BigDecimal money;

    private Boolean negativeBalance;

    private Long profileId;
}