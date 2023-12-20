package com.bank.history.service;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;
import com.bank.history.mapper.HistoryMapper;
import com.bank.history.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService{

    private final HistoryRepository historyRepository;
    private final HistoryMapper mapper;

    @Override
    @Transactional
    public void addHistory(Long id, String entityType) {
        log.info("Сохраняем запись");
        HistoryEntity historyEntity = new HistoryEntity();

        switch (entityType) {
            case "transferAudit":
                historyEntity.setTransferAuditId(id);
                break;
            case "profileAudit":
                historyEntity.setProfileAuditId(id);
                break;
            case "accountAudit":
                historyEntity.setAccountAuditId(id);
                break;
            case "antiFraudAudit":
                historyEntity.setAntiFraudAuditId(id);
                break;
            case "publicBankInfoAudit":
                historyEntity.setPublicBankInfoAuditId(id);
                break;
            case "authorizationAudit":
                historyEntity.setAuthorizationAuditId(id);
                break;

            default:
                break;
        }

        historyRepository.save(historyEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public HistoryDto findByEntity(Long id, String entityType) {
        log.info("поиск события");
        Optional<HistoryEntity> optional = Optional.empty();

        switch (entityType) {
            case "transferAudit":
                optional = historyRepository.findByTransferAuditId(id);
                break;
            case "profileAudit":
                optional = historyRepository.findByProfileAuditId(id);
                break;
            case "accountAudit":
                optional = historyRepository.findByAccountAuditId(id);
                break;
            case "antiFraudAudit":
                optional = historyRepository.findByAntiFraudAuditId(id);
                break;
            case "publicBankInfoAudit":
                optional = historyRepository.findByPublicBankInfoAuditId(id);
                break;
            case "authorizationAudit":
                optional = historyRepository.findByAuthorizationAuditId(id);
                break;

            default:
                break;
        }

        HistoryEntity entity = optional.orElseThrow(() ->
                            new EntityNotFoundException("History with " + entityType + "Id = "
                                    + id + " not found"));
        return mapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public HistoryDto findByHistoryId(Long id) {
        log.info("поиск записи HistoryEntity");
        HistoryEntity entity = historyRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("History with id = "
                                + id + " not found"));
        return mapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistoryDto> findAllHistories() {
        log.info("поиск записи HistoryEntity");
        return mapper.toDtoList(historyRepository.findAll());
    }


    @Override
    @Transactional(readOnly = true)
    public List<HistoryDto> findAllByEntityType(String entityType) {
        log.info("поиск всех записей HistoryEntity");
        List<HistoryEntity> entityList = Collections.emptyList();

        switch (entityType) {
            case "transferAudit":
                entityList = historyRepository.findAllByTransferAuditIdNotNull();
                break;
            case "profileAudit":
                entityList = historyRepository.findAllByProfileAuditIdNotNull();
                break;
            case "accountAudit":
                entityList = historyRepository.findAllByAccountAuditIdNotNull();
                break;
            case "antiFraudAudit":
                entityList = historyRepository.findAllByAntiFraudAuditIdNotNull();
                break;
            case "publicBankInfoAudit":
                entityList = historyRepository.findAllByPublicBankInfoAuditIdNotNull();
                break;
            case "authorizationAudit":
                entityList = historyRepository.findAllByAuthorizationAuditIdNotNull();
                break;

            default:
                break;
        }
        return mapper.toDtoList(entityList);
    }


    @Override
    @Transactional
    public HistoryDto update(Long id, HistoryDto dto) {
        log.info("обновление записи HistoryEntity");
        HistoryEntity entity = historyRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("History with id = "
                                + id + " not found"));
        if (Objects.nonNull(dto.getTransferAuditId())) {
            entity.setTransferAuditId(dto.getTransferAuditId());
        }
        if (Objects.nonNull(dto.getAccountAuditId())) {
            entity.setAccountAuditId(dto.getAccountAuditId());
        }
        if (Objects.nonNull(dto.getAntiFraudAuditId())) {
            entity.setAntiFraudAuditId(dto.getAntiFraudAuditId());
        }
        if (Objects.nonNull(dto.getProfileAuditId())) {
            entity.setProfileAuditId(dto.getProfileAuditId());
        }
        if (Objects.nonNull(dto.getAuthorizationAuditId())) {
            entity.setAuthorizationAuditId(dto.getAuthorizationAuditId());
        }
        if (Objects.nonNull(dto.getPublicBankInfoAuditId())) {
            entity.setPublicBankInfoAuditId(dto.getPublicBankInfoAuditId());
        }

        historyRepository.save(entity);
        return mapper.toDto(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("удаление записи HistoryEntity");
        historyRepository.deleteById(id);

    }
}
