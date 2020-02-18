package com.studio.harbour.jdbc.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.studio.harbour.jdbc.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	@Query("select * from User u where u.email = :email")
	public Optional<User> findByEmail(@Param("email") String email);

	@Query("select * from User u where u.username = :username")
	public User findByUsername(@Param("username") String username);

}
