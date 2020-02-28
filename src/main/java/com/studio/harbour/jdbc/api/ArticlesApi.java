package com.studio.harbour.jdbc.api;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.studio.harbour.jdbc.domain.Article;
import com.studio.harbour.jdbc.domain.User;
import com.studio.harbour.jdbc.json.ArticleData;
import com.studio.harbour.jdbc.service.ArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping(path = "/articles")
public class ArticlesApi {
	private ArticleService articleService;
	
	@Autowired
	public ArticlesApi(ArticleService articleService) {
		this.articleService = articleService;
	}
	
	@PostMapping
	public ResponseEntity<ArticleData> createArticle(@Valid @RequestBody NewArticleParam newArticleParam,
									@AuthenticationPrincipal User currentUser) {
		
		Article article = Article.builder()
						 	.title(newArticleParam.getTitle())
						 	.description(newArticleParam.getDescription())						
						 	.body(newArticleParam.getBody())
						 	.userId(currentUser.getId()).build();
		
		String[] tagList = newArticleParam.getTagList();
		
		ArticleData articleData = articleService.createArticle(article, tagList);
		
		return ResponseEntity.ok(articleData);
	}
}

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("article")
class NewArticleParam {
    @NotBlank(message = "can't be empty")
    private String title;
    @NotBlank(message = "can't be empty")
    private String description;
    @NotBlank(message = "can't be empty")
    private String body;
    private String[] tagList;
}
