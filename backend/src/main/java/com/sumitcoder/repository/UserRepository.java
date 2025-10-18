package com.sumitcoder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sumitcoder.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
}
