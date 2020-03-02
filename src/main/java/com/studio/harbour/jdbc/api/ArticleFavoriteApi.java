package com.studio.harbour.jdbc.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studio.harbour.jdbc.domain.User;
import com.studio.harbour.jdbc.json.ArticleData;
import com.studio.harbour.jdbc.service.ArticleService;

@RestController
@RequestMapping(path = "/articles/{slug}/favorite")
public class ArticleFavoriteApi {
	private ArticleService articleService;
	
	@Autowired
	public ArticleFavoriteApi(ArticleService articleService) {
		this.articleService = articleService;
	}
	
	
	@PostMapping
	public ResponseEntity<ArticleData> favorite(@PathVariable("slug") String slug,
									  @AuthenticationPrincipal User currentUser) {
		
		Optional<ArticleData> articleData = articleService.favorite(slug, currentUser);		
		return ResponseEntity.of(articleData);
	}
	
	@DeleteMapping
	public ResponseEntity<ArticleData> unfavorite(@PathVariable("slug") String slug,
										@AuthenticationPrincipal User currentUser) {
		
		Optional<ArticleData> articleData = articleService.unfavorite(slug, currentUser);
		return ResponseEntity.of(articleData);
	}
}