package com.studio.harbour.jdbc.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.studio.harbour.jdbc.json.ArticleData;

@Repository
public interface AdvancedArticleRepository {
	
	public List<Long> findArticles(String tag, String author, String favoritedBy, Pageable page);
	
	public List<ArticleData> fetchArticles(List<Long> idList, Long userId);
}
