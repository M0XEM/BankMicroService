package com.bank.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDTO {
    private Long profileId;
    private Long bankDetailsId;
    private Boolean negativeBalance;
}
