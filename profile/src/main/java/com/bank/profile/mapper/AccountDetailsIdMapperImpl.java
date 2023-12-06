package com.bank.profile.mapper;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.mapper.interfaces.AccountDetailsIdMapper;
import com.bank.profile.model.AccountDetailsId;
import com.bank.profile.repository.ProfileRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
public class AccountDetailsIdMapperImpl implements AccountDetailsIdMapper {
    private final ProfileRepository profileRepository;

    public AccountDetailsIdMapperImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public AccountDetailsId toEntity(AccountDetailsIdDto dto) {
        return AccountDetailsId.builder()
                .accountId(dto.getAccountId())
                .profile(
                        profileRepository.findById(dto.getProfileId())
                                .orElseThrow(() -> new EntityNotFoundException("toEntity is null"))
                )
                .build();
    }

    public AccountDetailsIdDto toDto(AccountDetailsId entity) {
        return AccountDetailsIdDto.builder()
                .profileId(entity.getProfile().getId())
                .accountId(entity.getAccountId())
                .build();
    }

}
