package com.bank.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {

    @NotNull
    private Long id;

    @Positive
    private BigDecimal amount;

    private String type; // Тип операции: DEPOSIT, WITHDRAW, TRANSFER

    private Long targetId; // Для переводов (TRANSFER)

    @AssertTrue(message = "targetAccountId для типа TRANSFER не может быть пустым")
    private boolean isTargetAccountIdValid() {
        return !"TRANSFER".equals(type) || (targetId != null);
    }
}