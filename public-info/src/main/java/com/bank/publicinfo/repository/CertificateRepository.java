package com.bank.publicinfo.repository;

import com.bank.publicinfo.entity.CertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificateRepository extends JpaRepository<CertificateEntity, Long> {
    void deleteByIdAndBankDetails_Id(Long id, Long bankDetailsId);

    List<CertificateEntity> findAllByBankDetails_Id(Long bankDetailsId);
}
