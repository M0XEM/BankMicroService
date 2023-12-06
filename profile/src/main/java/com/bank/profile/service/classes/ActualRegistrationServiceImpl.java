package com.bank.profile.service.classes;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.mapper.ActualRegistrationMapper;
import com.bank.profile.model.ActualRegistration;
import com.bank.profile.repository.ActualRegistrationRepository;
import com.bank.profile.service.interfaces.ActualRegistrationService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ActualRegistrationServiceImpl extends BaseServiceImpl<
        ActualRegistrationDto,
        ActualRegistration>
        implements ActualRegistrationService {

    private final ActualRegistrationRepository repository;
    private final ActualRegistrationMapper mapper;

    public ActualRegistrationServiceImpl(ActualRegistrationRepository repository, ActualRegistrationMapper mapper) {
        super(repository, mapper, ActualRegistration.class);
        this.repository = repository;
        this.mapper = mapper;
    }
}
