package com.bank.profile.mapper;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.mapper.interfaces.BaseMapper;
import com.bank.profile.model.ActualRegistration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActualRegistrationMapper extends BaseMapper<ActualRegistrationDto, ActualRegistration> {
    ActualRegistration toEntity(ActualRegistrationDto entity);

    ActualRegistrationDto toDto(ActualRegistration entity);
}
