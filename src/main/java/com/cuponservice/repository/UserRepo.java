package com.cuponservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cuponservice.mode.User;

public interface UserRepo extends JpaRepository<User, Long>{
	User findByEmail(String email);
}
