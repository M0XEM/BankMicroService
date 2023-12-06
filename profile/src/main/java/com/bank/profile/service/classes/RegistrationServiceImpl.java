package com.bank.profile.service.classes;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.mapper.RegistrationMapper;
import com.bank.profile.model.Registration;
import com.bank.profile.repository.RegistrationRepository;
import com.bank.profile.service.interfaces.RegistrationService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RegistrationServiceImpl extends BaseServiceImpl<
        RegistrationDto,
        Registration>
        implements RegistrationService {

    private final RegistrationRepository repository;
    private final RegistrationMapper mapper;

    public RegistrationServiceImpl(RegistrationRepository repository, RegistrationMapper mapper) {
        super(repository, mapper, Registration.class);
        this.repository = repository;
        this.mapper = mapper;
    }
}
