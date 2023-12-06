package com.bank.profile.mapper.interfaces;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.model.AccountDetailsId;

public interface BaseMapper <DTO, ENTITY>{

    ENTITY toEntity(DTO dto);

    DTO toDto(ENTITY entity);
}
