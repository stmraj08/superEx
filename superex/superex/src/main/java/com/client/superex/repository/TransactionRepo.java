package com.client.superex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.client.superex.entity.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserEmail(String email);

    List<Transaction> findByStatus(String status);

}