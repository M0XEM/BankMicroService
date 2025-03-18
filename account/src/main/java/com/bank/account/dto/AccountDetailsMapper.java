package com.bank.account.dto;

import com.bank.account.entity.AccountDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountDetailsMapper {

    @Mapping(target = "id", ignore = true) // ID генерируется БД
    @Mapping(target = "accountNumber", ignore = true) // Генерируется отдельно
    AccountDetails toEntity(AccountDetailsCreateRequest request);

    AccountDetailsDTO toDto(AccountDetails accountDetails);
}