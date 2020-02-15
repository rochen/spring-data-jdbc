package com.studio.harbour.jdbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studio.harbour.jdbc.domain.Article;
import com.studio.harbour.jdbc.repository.ArticleRepository;

@Service
public class ArticleService {
	private ArticleRepository articleRepo;
		
	@Autowired
	public ArticleService(ArticleRepository articleRepo) {
		this.articleRepo = articleRepo;		
	}
	
	public Iterable<Article> getAll() {
		return articleRepo.findAll();
	}
}
