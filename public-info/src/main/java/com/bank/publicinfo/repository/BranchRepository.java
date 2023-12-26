package com.bank.publicinfo.repository;

import com.bank.publicinfo.entity.BranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<BranchEntity, Long> {
    Optional<BranchEntity> findByPhoneNumber(Long phoneNumber);
}
