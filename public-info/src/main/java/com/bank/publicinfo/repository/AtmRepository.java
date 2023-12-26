package com.bank.publicinfo.repository;

import com.bank.publicinfo.entity.AtmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtmRepository extends JpaRepository<AtmEntity, Long> {
    List<AtmEntity> findAllByBranch_Id(Long branchId);
}
