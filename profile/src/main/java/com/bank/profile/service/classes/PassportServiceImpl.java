package com.bank.profile.service.classes;

import com.bank.profile.aspect.annotation.Auditable;
import com.bank.profile.dto.AuditDto;
import com.bank.profile.dto.PassportDto;
import com.bank.profile.exception.NotFoundExceptionEntity;
import com.bank.profile.mapper.AuditMapper;
import com.bank.profile.mapper.PassportMapperImpl;
import com.bank.profile.mapper.interfaces.PassportMapper;
import com.bank.profile.model.Audit;
import com.bank.profile.model.Passport;
import com.bank.profile.repository.AuditRepository;
import com.bank.profile.repository.PassportRepository;
import com.bank.profile.service.interfaces.PassportService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PassportServiceImpl extends BaseServiceImpl<
        PassportDto,
        Passport>
        implements PassportService {

    private final PassportRepository repository;
    private final PassportMapper mapper;

    public PassportServiceImpl(PassportRepository repository, PassportMapper mapper) {
        super(repository, mapper, Passport.class);
        this.repository = repository;
        this.mapper = mapper;
    }
}
