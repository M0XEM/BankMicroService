package com.bank.publicinfo.repository;

import com.bank.publicinfo.entity.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends JpaRepository<AuditEntity, Long> {
    AuditEntity findByEntityJson(String entityJson);
}
