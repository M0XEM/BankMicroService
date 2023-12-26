package com.bank.publicinfo.repository;

import com.bank.publicinfo.entity.LicenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseRepository extends JpaRepository<LicenseEntity, Long> {
    void deleteByIdAndBankDetails_Id(Long id, Long bankDetailsId);

    List<LicenseEntity> findAllByBankDetails_Id(Long bankDetailsId);
}
