package com.studio.harbour.jdbc.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.studio.harbour.jdbc.domain.Comment;
import com.studio.harbour.jdbc.json.CommentData;
import com.studio.harbour.jdbc.mapper.CommentRowMapper;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
	
	@Query(value = "select c.id, c.body, c.created_at, c.updated_at, u.username, u.bio, u.image, f.follow "
			+ "from comment c "
			+ "join user u on u.id = c.user_id "
			+ "left join follow_ref f on f.follow = u.id and f.user = :user "
			+ "where c.id = :id",
			rowMapperClass = CommentRowMapper.class)
	public Optional<CommentData> findCommentDataById(@Param("id") Long id, @Param("user") Long user);
	
	@Query(value = "select * from comment c where c.article_id = :articleId and c.id = :id")
	public Optional<Comment> findByArticleAndId(@Param("articleId") Long articleId, @Param("id") Long id);
}
