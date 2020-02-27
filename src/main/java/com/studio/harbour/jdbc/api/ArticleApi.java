package com.studio.harbour.jdbc.api;

import java.util.Iterator;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.studio.harbour.jdbc.domain.Article;
import com.studio.harbour.jdbc.domain.User;
import com.studio.harbour.jdbc.json.ArticleData;
import com.studio.harbour.jdbc.service.ArticleService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping(path = "/articles/{slug}")
public class ArticleApi {
	private ArticleService articleService;
	
	@Autowired
	public ArticleApi(ArticleService articleService) {
		this.articleService = articleService;
	}
	
	@GetMapping(path = "/{slug}")
	public ResponseEntity<Article> getArticle(@PathVariable("slug") String slug) {
		Iterable<Article> all = articleService.getAll();
		Iterator<Article> iterator = all.iterator();
		iterator.hasNext();
		Article next = iterator.next();
		
		return ResponseEntity.ok(next);
	}
	
	@PutMapping
	public ResponseEntity<ArticleData> updateArticle(@PathVariable("slug") String slug,
													 @AuthenticationPrincipal User currentUser,
													 @Valid @RequestBody UpdateArticleParam updateArticleParam) {
		
		String title = updateArticleParam.getTitle();
		String description = updateArticleParam.getDescription();
		String body = updateArticleParam.getBody();
		Optional<ArticleData> updateArticle = articleService.updateArticle(slug, title, description, body, currentUser);
		
		return ResponseEntity.of(updateArticle);
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteArticle(@PathVariable("slug") String slug,
										   @AuthenticationPrincipal User currentUser) {
		
		boolean deleted = articleService.deleteArticle(slug, currentUser);
		ResponseEntity<?> response = deleted? ResponseEntity.noContent().build(): ResponseEntity.notFound().build();
		return response;
	}
}

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("article")
class UpdateArticleParam {
    private String title;
    private String body;
    private String description;
}