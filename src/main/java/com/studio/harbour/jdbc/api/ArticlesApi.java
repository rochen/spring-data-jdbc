package com.studio.harbour.jdbc.api;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.studio.harbour.jdbc.domain.Article;
import com.studio.harbour.jdbc.domain.User;
import com.studio.harbour.jdbc.json.ArticleData;
import com.studio.harbour.jdbc.repository.ArticleRepository;
import com.studio.harbour.jdbc.service.ArticleService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping(path = "/articles")
public class ArticlesApi {
	private ArticleService articleService;
	
	@Autowired
	ArticleRepository repo;
	
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
		
		return ResponseEntity.status(201).body(articleData);
	}
	
    @GetMapping(path = "feed")
    public ResponseEntity<?> getFeed(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                  @RequestParam(value = "limit", defaultValue = "20") int limit,
                                  @AuthenticationPrincipal User user) {
    	
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getArticles(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                      @RequestParam(value = "limit", defaultValue = "20") int limit,
                                      @RequestParam(value = "tag", required = false) String tag,
                                      @RequestParam(value = "favorited", required = false) String favoritedBy,
                                      @RequestParam(value = "author", required = false) String author,
                                      @AuthenticationPrincipal User user) {
        
    	ArticleData findByTest = repo.findArticleDataById(user.getId(), 1L);
    	return ResponseEntity.ok(findByTest);
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
