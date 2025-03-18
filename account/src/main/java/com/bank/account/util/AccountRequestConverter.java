package com.bank.account.util;

import com.bank.account.dto.AccountDetailsCreateRequest;
import com.bank.account.dto.ProfileResponseDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountRequestConverter {
    public static AccountDetailsCreateRequest convert(AccountDetailsCreateRequest request, ProfileResponseDTO profileData) {
        System.out.println("Внутри конвертера" + profileData.getProfileId());
        return AccountDetailsCreateRequest.builder()
                .passportId(request.getPassportId())
                .bankDetailsId(profileData.getBankDetailsId())
                .money(request.getMoney())
                .negativeBalance(profileData.getNegativeBalance())
                .profileId(profileData.getProfileId())
                .build();
    }
}
