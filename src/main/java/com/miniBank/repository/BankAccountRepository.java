package com.miniBank.repository;

import com.miniBank.model.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    Optional<BankAccount> findByUserId(String userId);
    Optional<BankAccount> findByAccountNumber(String accountNumber);

    @Modifying
    @Query(value = "UPDATE mst_bank_account SET balance = ?1 WHERE id = ?2", nativeQuery = true)
    void updateSaldoAccount(Long balance, String id);
}
