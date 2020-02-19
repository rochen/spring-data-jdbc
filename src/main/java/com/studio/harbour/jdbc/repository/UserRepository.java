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

	@Query("select * from User u where u.email = :email")
	public Optional<User> findByEmail(@Param("email") String email);


	@Query(rowMapperClass = ProfileRowMapper.class,
		   value = "select * from User u "
				 + "left join follow_ref f on u.id = f.follow and f.user = :me "
				 + "where u.username = :username")
	public ProfileData findByUsername(@Param("username") String username, @Param("me") Long myId);

}
