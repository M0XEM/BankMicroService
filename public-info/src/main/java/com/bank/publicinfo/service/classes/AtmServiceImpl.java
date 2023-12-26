package com.bank.publicinfo.service.classes;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.entity.AtmEntity;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.mapper.AtmMapper;
import com.bank.publicinfo.repository.AtmRepository;
import com.bank.publicinfo.service.interfaces.AtmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class AtmServiceImpl extends MainServiceClass<AtmDto, AtmEntity> implements AtmService {
    private final AtmRepository atmRepository;
    private final AtmMapper atmMapper;

    public AtmServiceImpl(AtmRepository atmRepository, AtmMapper atmMapper) {
        super(atmMapper, atmRepository, AtmEntity.class);
        this.atmRepository = atmRepository;
        this.atmMapper = atmMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AtmDto> findAllByBranchId(Long branchId) {
        return atmMapper.toDtoList(atmRepository.findAllByBranch_Id(branchId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AtmDto> findAll() {
        return atmMapper.toDtoList(atmRepository.findAll());
    }

    @Override
    public AtmDto save(AtmDto dto) {
        AtmEntity entity = atmRepository.save(atmMapper.toEntity(dto));
        try {
            log.info("Банкомат с id - \"{}\" для банка с id - \"{}\" сохранен в базе данных",
                    entity.getId(), entity.getBranch().getId());
        } catch (NullPointerException e) {
            log.info("Банкомат с id - \"{}\" без отделения банка сохранен в базе данных",
                    entity.getId());
        }
        return atmMapper.toDto(entity);
    }

    @Override
    public AtmDto update(Long id, AtmDto dto) {
        dto.setId(id);
        AtmEntity entity = atmRepository.save(atmMapper.toEntity(dto));
        try {
            log.info("Банкомат с id - \"{}\" для банка с id - \"{}\" обновлен в базе данных",
                    entity.getId(), entity.getBranch().getId());
        } catch (NullPointerException e) {
            log.info("Банкомат с id - \"{}\" без отделения банка обновлен в базе данных",
                    entity.getId());
        }
        return atmMapper.toDto(entity);
    }

    @Override
    public void deleteById(Long id) {
        try {
            atmRepository.deleteById(id);
            log.info("Банкомат с id - \"{}\" удален из базы данных", id);
        } catch (Exception e) {
            log.error("Банкомата с id - \"{}\" в базе данных не существует", id);
            throw new NotFoundException("Банкомата с заданными параметрами не существует");
        }
    }
}
