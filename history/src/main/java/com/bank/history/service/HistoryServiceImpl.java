package com.bank.history.service;

import com.bank.history.entity.HistoryEntity;
import com.bank.history.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService{

    private final HistoryRepository historyRepository;

    @Override
    @Transactional
    public void addHistory(Long id, String entityType) {
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
    public HistoryEntity findByEntity(Long id, String entityType) {
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

        return optional.orElseThrow(() ->
                            new EntityNotFoundException("History with " + entityType + "Id = "
                                    + id + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public HistoryEntity findByHistoryId(Long id) {
        return historyRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("History with id = "
                                + id + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistoryEntity> findAllHistories() {
        return historyRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistoryEntity> findAllByEntityType(String entityType) {
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

        return entityList;
    }

}
