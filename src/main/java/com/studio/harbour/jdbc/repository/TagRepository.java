package com.studio.harbour.jdbc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.studio.harbour.jdbc.domain.Tag;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {
	
}
