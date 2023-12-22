package com.bank.profile.mapper;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.dto.ProfileDto;
import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.mapper.interfaces.BaseMapper;
import com.bank.profile.model.Profile;
import com.bank.profile.model.Registration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegistrationMapper extends BaseMapper<RegistrationDto, Registration> {

}
