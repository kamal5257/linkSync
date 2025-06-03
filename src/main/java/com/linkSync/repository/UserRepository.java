package com.linkSync.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.linkSync.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "SELECT * from users where username =:userName", nativeQuery = true)
	User findByUserName(String userName);

	User findByEmail(String email);
}
