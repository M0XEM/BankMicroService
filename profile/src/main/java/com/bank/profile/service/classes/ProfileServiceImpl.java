package com.bank.profile.service.classes;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.mapper.interfaces.ProfileMapper;
import com.bank.profile.model.Profile;
import com.bank.profile.repository.ProfileRepository;
import com.bank.profile.service.interfaces.ProfileService;

import javax.transaction.Transactional;

@org.springframework.stereotype.Service
@Transactional
public class ProfileServiceImpl extends BaseServiceImpl<
        ProfileDto,
        Profile>
        implements ProfileService {

    private final ProfileRepository repository;
    private final ProfileMapper mapper;

    public ProfileServiceImpl(ProfileRepository repository, ProfileMapper mapper) {
        super(repository, mapper, Profile.class);
        this.repository = repository;
        this.mapper = mapper;
    }

}
