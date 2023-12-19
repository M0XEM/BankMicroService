package com.bank.publicinfo.service.classes;

import com.bank.publicinfo.aspect.annotation.Auditable;
import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entity.BankDetailsEntity;
import com.bank.publicinfo.exception.BadRequestException;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.mapper.BankDetailsMapper;
import com.bank.publicinfo.repository.BankDetailsRepository;
import com.bank.publicinfo.service.interfaces.BankDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BankDetailsServiceImpl implements BankDetailsService {
    private final BankDetailsRepository bankDetailsRepository;
    private final BankDetailsMapper bankDetailsMapper;

    @Override
    @Transactional(readOnly = true)
    public BankDetailsDto findById(Long id) {
        return bankDetailsMapper.toDto(bankDetailsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Нет банка с таким id - " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public BankDetailsDto findByBik(Long bik) {
        return bankDetailsMapper.toDto(bankDetailsRepository.findByBik(bik)
                .orElseThrow(() -> new NotFoundException("Нет банка с таким bik - " + bik)));
    }

    @Override
    @Transactional(readOnly = true)
    public BankDetailsDto findByInn(Long inn) {
        return bankDetailsMapper.toDto(bankDetailsRepository.findByInn(inn)
                .orElseThrow(() -> new NotFoundException("Нет банка с таким ИНН - " + inn)));
    }

    @Override
    @Transactional(readOnly = true)
    public BankDetailsDto findByKpp(Long kpp) {
        return bankDetailsMapper.toDto(bankDetailsRepository.findByKpp(kpp)
                .orElseThrow(() -> new NotFoundException("Нет банка с таким КПП - " + kpp)));
    }

    @Override
    @Transactional(readOnly = true)
    public BankDetailsDto findByCorAccount(Integer corAccount) {
        return bankDetailsMapper.toDto(bankDetailsRepository.findByCorAccount(corAccount)
                .orElseThrow(() -> new NotFoundException("Нет банка с таким корр. счётом - " + corAccount)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankDetailsDto> findAll() {
        return bankDetailsMapper.toDtoList(bankDetailsRepository.findAll());
    }

    @Override
    @Auditable(entityType = "bankDetails", operationType = "save")
    public BankDetailsDto save(BankDetailsDto dto) {
        if (dto == null) {
            throw new BadRequestException("В запросе нет данных о банке");
        }
        BankDetailsEntity entity = bankDetailsRepository.save(bankDetailsMapper.toEntity(dto));
        log.info("Банк с id - \"{}\" сохранен в базе данных", entity.getId());
        return bankDetailsMapper.toDto(entity);
    }

    @Override
    @Auditable(entityType = "bankDetails", operationType = "update")
    public BankDetailsDto update(Long id, BankDetailsDto dto) {
        if (dto == null) {
            throw new BadRequestException("В запросе нет данных о банке");
        }
        dto.setId(id);
        BankDetailsEntity entity = bankDetailsRepository.save(bankDetailsMapper.toEntity(dto));
        log.info("Банк с id - \"{}\" обновлен в базе данных", entity.getId());
        return bankDetailsMapper.toDto(entity);
    }

    @Override
    @Auditable(entityType = "bankDetails", operationType = "delete")
    public void deleteById(Long id) {
        try {
            bankDetailsRepository.deleteById(id);
            log.info("Банк с id - \"{}\" удален из базы данных", id);
        } catch (Exception e) {
            log.error("Банка с id - \"{}\" в базе данных не существует", id);
            throw new NotFoundException("Банка с заданным id не существует");
        }
    }
}
