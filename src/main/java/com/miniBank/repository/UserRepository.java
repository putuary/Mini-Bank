package com.miniBank.repository;

import com.miniBank.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query(value = "SELECT * FROM mst_user WHERE username = ?1", nativeQuery = true)
    Optional<User> findUsername(String username);


    @Query(value = "SELECT * FROM mst_user WHERE id = ?1", nativeQuery = true)
    Optional<User> findId(String id);
}