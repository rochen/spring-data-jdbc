package com.studio.harbour.jdbc.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.studio.harbour.jdbc.domain.User;
import com.studio.harbour.jdbc.json.ProfileData;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	@Query("SELECT * FROM USER u WHERE u.email = :email")
	public Optional<User> findByEmail(@Param("email") String email);


	@Query(value = "SELECT * FROM USER u "
				 + "LEFT JOIN FOLLOW_REF f ON u.id = f.follow AND f.user = :me "
				 + "WHERE u.username = :username")
	public Optional<ProfileData> findByUsername(@Param("username") String username, @Param("me") Long myId);

}
