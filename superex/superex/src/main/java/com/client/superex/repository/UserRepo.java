package com.client.superex.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.client.superex.entity.User;

public interface UserRepo extends JpaRepository<User,Long>{
	User findByEmail(String email);
	
} 