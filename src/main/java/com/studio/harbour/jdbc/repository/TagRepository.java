package com.studio.harbour.jdbc.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.studio.harbour.jdbc.domain.Tag;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {

	@Query("select case when count(t.name) > 0 then true else false end from Tag t where name = :name")
	public boolean existsByName(@Param("name") String name);
	
	@Query("select * from tag where name=:name")
	public Optional<Tag> findByName(@Param("name") String name);
	
}
