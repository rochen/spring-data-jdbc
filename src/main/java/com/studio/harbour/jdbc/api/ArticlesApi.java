package com.studio.harbour.jdbc.api;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studio.harbour.jdbc.domain.Article;
import com.studio.harbour.jdbc.json.ArticleData;
import com.studio.harbour.jdbc.json.ProfileData;
import com.studio.harbour.jdbc.mapper.ArticleMapper;
import com.studio.harbour.jdbc.service.ArticleService;
import com.studio.harbour.jdbc.service.ProfileService;

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
	
	@Autowired
	ArticleMapper artmap;

	@Autowired
	ProfileService userService;
	
	@GetMapping(path = "/{slug}")
	public ResponseEntity<ArticleData> getArticle(@PathVariable("slug") String slug) {
		Iterable<Article> all = articleService.getAll();
		Iterator<Article> iterator = all.iterator();
		iterator.hasNext();
		Article next = iterator.next();
		ArticleData userToArticleData = artmap.userToArticleData(next);
		
		ProfileData findByUsername = userService.findByUsername("robinly");
		userToArticleData.setProfileData(findByUsername);
		return ResponseEntity.ok(userToArticleData);
	}
}
