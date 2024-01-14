package com.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.login.entities.User;

@Repository
public interface FormRepository extends JpaRepository<User, Integer> {

	@Query("select u from User u where u.username =:username")
	public User getUserByUsername(@Param("username") String username);
}
