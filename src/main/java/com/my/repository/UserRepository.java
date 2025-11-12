package com.my.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.my.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findUserByLogin(String login);
	
}
