package com.bank.profile.mapper;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.exception.NotFoundExceptionEntity;
import com.bank.profile.mapper.interfaces.ProfileMapper;
import com.bank.profile.model.Profile;
import com.bank.profile.repository.ActualRegistrationRepository;
import com.bank.profile.repository.PassportRepository;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapperImpl implements ProfileMapper {
    private final ActualRegistrationRepository actualRegistrationRepository;
    private final PassportRepository passportRepository;

    public ProfileMapperImpl(ActualRegistrationRepository actualRegistrationRepository, PassportRepository passportRepository) {
        this.actualRegistrationRepository = actualRegistrationRepository;
        this.passportRepository = passportRepository;
    }

    @Override
    public Profile toEntity(ProfileDto dto) {
        return Profile.builder()
                .email(dto.getEmail())
                .actualRegistration(
                        actualRegistrationRepository.findById(
                                dto.getActualRegistrationId())
                                .orElseThrow(() -> new NotFoundExceptionEntity("toEntity actualRegistration is null")
                                )
                )
                .passport(
                        passportRepository.findById(
                                        dto.getPassport_id())
                                .orElseThrow(() -> new NotFoundExceptionEntity("toEntity passport is null")
                                )
                )
                .inn(dto.getInn())
                .nameOnCard(dto.getNameOnCard())
                .phoneNumber(dto.getPhoneNumber())
                .snils(dto.getSnils())
                .build();
    }

    @Override
    public ProfileDto toDto(Profile profile) {
        return ProfileDto.builder()
                .email(profile.getEmail())
                .actualRegistrationId(profile.getActualRegistration().getId())
                .passport_id(profile.getPassport().getId())
                .inn(profile.getInn())
                .nameOnCard(profile.getNameOnCard())
                .phoneNumber(profile.getPhoneNumber())
                .snils(profile.getSnils())
                .build();
    }
}
