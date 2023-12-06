package com.bank.profile.service.classes;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.mapper.interfaces.AccountDetailsIdMapper;
import com.bank.profile.model.AccountDetailsId;
import com.bank.profile.repository.AccountDetailsIdRepository;
import com.bank.profile.service.interfaces.AccountDetailsIdService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AccountDetailsIdServiceImpl extends BaseServiceImpl<
        AccountDetailsIdDto,
        AccountDetailsId>
        implements AccountDetailsIdService {

    private final AccountDetailsIdRepository repository;
    private final AccountDetailsIdMapper mapper;
    public AccountDetailsIdServiceImpl(AccountDetailsIdRepository repository, AccountDetailsIdMapper mapper) {
        super(repository, mapper, AccountDetailsId.class);
        this.repository = repository;
        this.mapper = mapper;
    }

}