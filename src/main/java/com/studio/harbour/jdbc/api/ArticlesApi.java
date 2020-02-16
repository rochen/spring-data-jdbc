package com.studio.harbour.jdbc.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studio.harbour.jdbc.domain.Article;
import com.studio.harbour.jdbc.service.ArticleService;

@RestController
@RequestMapping(path = "/articles")
public class ArticlesApi {
	private ArticleService articleService;
	
	@Autowired
	public ArticlesApi(ArticleService articleService) {
		this.articleService = articleService;
	}
	
	@GetMapping
	public ResponseEntity<Iterable<Article>> getArticles() {
		Iterable<Article> all = articleService.getAll();
		return ResponseEntity.ok(all);
	}
}
