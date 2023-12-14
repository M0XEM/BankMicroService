package com.bank.publicinfo.service.classes;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.BranchEntity;
import com.bank.publicinfo.exception.BadRequestException;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.mapper.BranchMapper;
import com.bank.publicinfo.repository.BranchRepository;
import com.bank.publicinfo.service.interfaces.BranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;

    @Override
    public BranchDto save(BranchDto branchDto) {
        if (branchDto == null) {
            throw new BadRequestException("В запросе нет данных об отделении банка");
        }
        BranchEntity entity = branchRepository.save(branchMapper.toEntity(branchDto));
        log.info("Отделение банка с id - \"{}\" сохранен в базе данных", entity.getId());
        return branchMapper.toDto(entity);
    }

    @Override
    public BranchDto findById(Long id) {
        return branchMapper.toDto(branchRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Нет отделения банка с таким id - " + id)));
    }

    @Override
    public BranchDto findByPhoneNumber(Long phoneNumber) {
        return branchMapper.toDto(branchRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("Нет отделения банка с таким номером телефона - " + phoneNumber)));
    }

    @Override
    public void deleteById(Long id) {
        try {
            branchRepository.deleteById(id);
            log.info("Отделение банка с id - \"{}\" удален из базы данных", id);
        } catch (Exception e) {
            log.error("Отделения банка с id - \"{}\" в базе данных не существует", id);
            throw new NotFoundException("Отделения банка с заданным id не существует");
        }
    }

    @Override
    public List<BranchDto> findAll() {
        return branchMapper.toDtoList(branchRepository.findAll());
    }
}
