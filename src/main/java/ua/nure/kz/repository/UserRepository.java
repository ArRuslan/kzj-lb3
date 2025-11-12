package ua.nure.kz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.nure.kz.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findUserByLogin(String login);
	
}
