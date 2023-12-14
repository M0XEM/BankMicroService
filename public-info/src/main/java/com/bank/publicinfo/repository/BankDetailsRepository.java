package com.bank.publicinfo.repository;

import com.bank.publicinfo.entity.BankDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankDetailsRepository extends JpaRepository<BankDetailsEntity, Long> {
    Optional<BankDetailsEntity> findByBik(Long bik);

    Optional<BankDetailsEntity> findByInn(Long inn);

    Optional<BankDetailsEntity> findByKpp(Long kpp);

    Optional<BankDetailsEntity> findByCorAccount(Integer corAccount);
}
