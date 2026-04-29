package com.client.superex.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.client.superex.entity.Admin;
public interface AdminRepo extends JpaRepository<Admin,Long>{
	Admin findByEmail(String email);
	} 