package com.bank.profile.mapper;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.exception.NotFoundExceptionEntity;
import com.bank.profile.mapper.interfaces.PassportMapper;
import com.bank.profile.model.Passport;
import com.bank.profile.repository.RegistrationRepository;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
public class PassportMapperImpl implements PassportMapper {
    private final RegistrationRepository registrationRepository;

    public PassportMapperImpl(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @Override
    public Passport toEntity(PassportDto passportDto) {
        return Passport.builder()
                .birthDate(passportDto.getBirthDate())
                .dateOfIssue(passportDto.getDateOfIssue())
                .divisionCode(passportDto.getDivisionCode())
                .birthPlace(passportDto.getBirthPlace())
                .expirationDate(passportDto.getExpirationDate())
                .firstName(passportDto.getFirstName())
                .lastName(passportDto.getLastName())
                .middleName(passportDto.getMiddleName())
                .gender(passportDto.getGender())
                .issuedBy(passportDto.getIssuedBy())
                .number(passportDto.getNumber())
                .series(passportDto.getSeries())
                .registrationId(registrationRepository.findById(passportDto.getRegistrationId())
                        .orElseThrow(() -> new NotFoundExceptionEntity("toEntity registration is null")))
                .build();
    }

    @Override
    public PassportDto toDto(Passport passport) {
        return PassportDto.builder()
                .birthDate(passport.getBirthDate())
                .dateOfIssue(passport.getDateOfIssue())
                .divisionCode(passport.getDivisionCode())
                .birthPlace(passport.getBirthPlace())
                .expirationDate(passport.getExpirationDate())
                .firstName(passport.getFirstName())
                .lastName(passport.getLastName())
                .middleName(passport.getMiddleName())
                .gender(passport.getGender())
                .issuedBy(passport.getIssuedBy())
                .number(passport.getNumber())
                .series(passport.getSeries())
                .registrationId(passport.getRegistrationId().getId())
                .build();
    }
}
