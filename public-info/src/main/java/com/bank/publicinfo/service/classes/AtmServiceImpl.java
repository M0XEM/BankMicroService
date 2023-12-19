package com.bank.publicinfo.service.classes;

import com.bank.publicinfo.aspect.annotation.Auditable;
import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.entity.AtmEntity;
import com.bank.publicinfo.exception.BadRequestException;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.mapper.AtmMapper;
import com.bank.publicinfo.repository.AtmRepository;
import com.bank.publicinfo.service.interfaces.AtmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AtmServiceImpl implements AtmService {
    private final AtmRepository atmRepository;
    private final AtmMapper atmMapper;

    @Override
    @Transactional(readOnly = true)
    public AtmDto findById(Long id) {
        return atmMapper.toDto(atmRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Нет банкомата с таким id - " + id)));
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
    @Auditable(entityType = "atm", operationType = "save")
    public AtmDto save(AtmDto dto) {
        if (dto == null) {
            throw new BadRequestException("В запросе нет данных о банкомате");
        }
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
    @Auditable(entityType = "atm", operationType = "update")
    public AtmDto update(Long id, AtmDto dto) {
        if (dto == null) {
            throw new BadRequestException("В запросе нет данных о банкомате");
        }
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
    @Auditable(entityType = "atm", operationType = "delete")
    public void deleteByAtmId(Long atmId) {
        try {
            atmRepository.deleteById(atmId);
            log.info("Банкомат с id - \"{}\" без отделения банка удален из базы данных", atmId);
        } catch (Exception e) {
            log.error("Банкомата с id - \"{}\" без отделения банка в базе данных не существует", atmId);
            throw new NotFoundException("Банкомата с заданными параметрами не существует");
        }
    }

    @Override
    @Auditable(entityType = "atm", operationType = "delete")
    public void deleteByAtmIdAndBranchId(Long atmId, Long branchId) {
        try {
            atmRepository.deleteByIdAndBranch_Id(atmId, branchId);
            log.info("Банкомат с id - \"{}\" для отделения банка с id - \"{}\" удален из базы данных", atmId, branchId);
        } catch (Exception e) {
            log.error("Банкомата с id - \"{}\" для отделения банка с id - \"{}\" в базе данных не существует", atmId, branchId);
            throw new NotFoundException("Банкомата с заданными параметрами не существует");
        }
    }
}
