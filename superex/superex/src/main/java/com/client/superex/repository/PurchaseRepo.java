package com.client.superex.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.client.superex.entity.Purchase;
import com.client.superex.entity.User;


public interface PurchaseRepo extends JpaRepository<Purchase,Long>{
	
	 List<Purchase> findByUser(User user);
	 List<Purchase> findByUserEmail(String email);
}