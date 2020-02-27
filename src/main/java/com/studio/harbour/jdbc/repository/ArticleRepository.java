package com.studio.harbour.jdbc.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.studio.harbour.jdbc.domain.Article;
import com.studio.harbour.jdbc.json.ArticleData;
import com.studio.harbour.jdbc.mapper.ArticleResultSetExtractor;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {
	
	@Query(value = "SELECT a.slug, a.title, a.description, a.body, a.created_at, a.updated_at, "
				+ "u.username, u.bio, u.image, t.name tag, f.follow following FROM ARTICLE a "
				+ "join user u on u.id = a.user_id "
				+ "left join article_tag at on at.article = a.id "
				+ "left join tag t on t.id = at.tag "
				+ "left join follow_ref f on f.follow = a.user_id and f.user = :user "
				+ "where a.id = :id",
			resultSetExtractorClass = ArticleResultSetExtractor.class)
	public ArticleData findArticleDataById(@Param("id") Long id, @Param("user") Long user);
	
	
	@Query(value = "select * from article a where a.slug = :slug")
	public Optional<Article> findBySlug(@Param("slug") String slug);
		
}
