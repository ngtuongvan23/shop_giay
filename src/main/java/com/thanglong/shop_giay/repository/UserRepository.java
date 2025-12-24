package com.thanglong.shop_giay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thanglong.shop_giay.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{

	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
	boolean existsByPhoneNumber(String phoneNumber);
	Optional<User> findByUsername(String username);
	Optional<User> findByPhoneNumber(String phoneNumber);
}
