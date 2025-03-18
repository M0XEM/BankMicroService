package com.bank.account.dto;

import com.bank.account.entity.AccountDetails;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-17T23:52:20+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class AccountDetailsMapperImpl implements AccountDetailsMapper {

    @Override
    public AccountDetails toEntity(AccountDetailsCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        AccountDetails.AccountDetailsBuilder accountDetails = AccountDetails.builder();

        accountDetails.passportId( request.getPassportId() );
        accountDetails.bankDetailsId( request.getBankDetailsId() );
        accountDetails.money( request.getMoney() );
        accountDetails.negativeBalance( request.getNegativeBalance() );
        accountDetails.profileId( request.getProfileId() );

        return accountDetails.build();
    }

    @Override
    public AccountDetailsDTO toDto(AccountDetails accountDetails) {
        if ( accountDetails == null ) {
            return null;
        }

        AccountDetailsDTO.AccountDetailsDTOBuilder accountDetailsDTO = AccountDetailsDTO.builder();

        accountDetailsDTO.id( accountDetails.getId() );
        accountDetailsDTO.passportId( accountDetails.getPassportId() );
        accountDetailsDTO.accountNumber( accountDetails.getAccountNumber() );
        accountDetailsDTO.money( accountDetails.getMoney() );
        accountDetailsDTO.negativeBalance( accountDetails.getNegativeBalance() );
        accountDetailsDTO.profileId( accountDetails.getProfileId() );
        accountDetailsDTO.bankDetailsId( accountDetails.getBankDetailsId() );

        return accountDetailsDTO.build();
    }
}
