package com.studio.harbour.jdbc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.slugify.Slugify;
import com.studio.harbour.jdbc.domain.Article;
import com.studio.harbour.jdbc.domain.Tag;
import com.studio.harbour.jdbc.domain.User;
import com.studio.harbour.jdbc.json.ArticleData;
import com.studio.harbour.jdbc.repository.ArticleRepository;
import com.studio.harbour.jdbc.repository.TagRepository;

@Service
public class ArticleService {
	private ArticleRepository articleRepo;
	private TagRepository tagRepo;
	
	@Autowired
	private Slugify slugify;
			
	@Autowired
	public ArticleService(ArticleRepository articleRepo, TagRepository tagRepo) {
		this.articleRepo = articleRepo;		
		this.tagRepo = tagRepo;
	}
	
	@Transactional
	public ArticleData createArticle(Article article, String[] tagList) {
		if(tagList != null) {
			for(String name: tagList) {
				Optional<Tag> optional = tagRepo.findByName(name);
				if(optional.isPresent()) {
					article.tag(optional.get());
				} else {
					// new a tag
					Tag tag = Tag.builder().name(name).build();
					tagRepo.save(tag);
					article.tag(tag);
				}			
			}
		}
		
		article.setSlug(slugify.slugify(article.getTitle()));
		articleRepo.save(article);
		
		ArticleData articleData = articleRepo.findArticleDataById(article.getId(), article.getUserId());
		return articleData;
	}
	
	@Transactional
	public Optional<ArticleData> updateArticle(String slug, String title, String description, String body, User currentUser) {
		ArticleData articleData = null;
		
		Optional<Article> optional = articleRepo.findBySlug(slug);
		if(optional.isPresent()) {
			Article article = optional.get();
			
			if(title != null) {
				article.setTitle(title);
				article.setSlug(slugify.slugify(title));
			}
			
			if(description != null)
				article.setDescription(description);
			
			if(body != null)
				article.setBody(body);
			
			articleRepo.save(article);		
			articleData = articleRepo.findArticleDataById(article.getId(), article.getUserId());
		} 
		
		return Optional.ofNullable(articleData);
	}
	
	@Transactional
	public boolean deleteArticle(String slug, User currentUser) {
		boolean deleted = false;
		Optional<Article> optional = articleRepo.findBySlug(slug);
		if(optional.isPresent()) {
			articleRepo.delete(optional.get());
			deleted = true;
		}
		return deleted;
	}
	
	public Optional<ArticleData> getArticle(String slug, User currentUser) {
		ArticleData articleData = null;
		Optional<Article> optional = articleRepo.findBySlug(slug);
		if(optional.isPresent()) {
			Article article = optional.get();
			Long articleId = article.getId();
			Long userId = currentUser == null? 0: currentUser.getId();
			articleData = articleRepo.findArticleDataById(articleId, userId);
		}
		return Optional.ofNullable(articleData);
	}
}
