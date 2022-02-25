package com.dandelic.BankManager.repository;

import com.dandelic.BankManager.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
