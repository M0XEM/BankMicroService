package com.bank.account.util;

import com.bank.account.entity.AccountDetails;
import lombok.experimental.UtilityClass;


public class AccountDetailsCloner {
    public static AccountDetails clone(AccountDetails original) {
        return AccountDetails.builder()
                .id(original.getId())
                .passportId(original.getPassportId())
                .accountNumber(original.getAccountNumber())
                .bankDetailsId(original.getBankDetailsId())
                .money(original.getMoney())
                .negativeBalance(original.getNegativeBalance())
                .profileId(original.getProfileId())
                .build();
    }
}
