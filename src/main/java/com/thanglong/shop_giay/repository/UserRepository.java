package com.thanglong.shop_giay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thanglong.shop_giay.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{

	boolean existsByEmail(String email);
	boolean existsByPhoneNumber(String phoneNumber);
	boolean existsByUserName(String userName);
	Optional<User> findByPhoneNumber(String phoneNumber);
	Optional<User> findByEmail(String Email);
	Optional<User> findByUserName(String userName);
}
