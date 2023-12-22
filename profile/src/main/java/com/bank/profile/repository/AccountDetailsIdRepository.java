package com.bank.profile.repository;

import com.bank.profile.model.AccountDetailsId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDetailsIdRepository extends JpaRepository<AccountDetailsId, Long> {
    void deleteAccountDetailsIdByProfileId(Long id);
}
