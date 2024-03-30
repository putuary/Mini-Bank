package com.miniBank.repository;

import com.miniBank.model.entity.Transaction;
import com.miniBank.model.response.TransactionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    @Query(value = "SELECT * FROM transaction WHERE account_id = ?1 OR dest_account_id = ?1", nativeQuery = true)
    List<Transaction> findAllAccountId(String AccountId);

    @Query(value = "SELECT * FROM transaction WHERE id = ?1 AND (account_id = ?2 OR dest_account_id = ?2);", nativeQuery = true)
    Optional<Transaction> findById(String Id, String AccountId);
}
