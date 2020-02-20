package com.studio.harbour.jdbc.api;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		
	@GetMapping(path = "/{slug}")
	public ResponseEntity<Article> getArticle(@PathVariable("slug") String slug) {
		Iterable<Article> all = articleService.getAll();
		Iterator<Article> iterator = all.iterator();
		iterator.hasNext();
		Article next = iterator.next();
		
		return ResponseEntity.ok(next);
	}
}
