package com.bank.history.repository;

import com.bank.history.entity.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface HistoryRepository extends JpaRepository<HistoryEntity, Long> {
    Optional<HistoryEntity> findByAccountAuditId(Long id);
    Optional<HistoryEntity> findByTransferAuditId(Long id);
    Optional<HistoryEntity> findByProfileAuditId(Long id);
    Optional<HistoryEntity> findByAntiFraudAuditId(Long id);
    Optional<HistoryEntity> findByPublicBankInfoAuditId(Long id);
    Optional<HistoryEntity> findByAuthorizationAuditId(Long id);

    List<HistoryEntity> findAllByAccountAuditIdNotNull();
    List<HistoryEntity> findAllByTransferAuditIdNotNull();
    List<HistoryEntity> findAllByProfileAuditIdNotNull();
    List<HistoryEntity> findAllByAntiFraudAuditIdNotNull();
    List<HistoryEntity> findAllByPublicBankInfoAuditIdNotNull();
    List<HistoryEntity> findAllByAuthorizationAuditIdNotNull();
}
