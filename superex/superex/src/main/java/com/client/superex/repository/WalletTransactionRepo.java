package com.client.superex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.client.superex.entity.WalletTransaction;

public interface WalletTransactionRepo extends JpaRepository<WalletTransaction, Long> {

    List<WalletTransaction> findByUserEmailOrderByCreatedAtDesc(String email);

}