package com.studio.harbour.jdbc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studio.harbour.jdbc.domain.Article;
import com.studio.harbour.jdbc.domain.Comment;
import com.studio.harbour.jdbc.domain.User;
import com.studio.harbour.jdbc.json.CommentData;
import com.studio.harbour.jdbc.repository.ArticleRepository;
import com.studio.harbour.jdbc.repository.CommentRepository;

@Service
public class CommentService {
	private CommentRepository commentRepo;
	private ArticleRepository articleRepo;
	
	@Autowired
	public CommentService(CommentRepository commentRepo, ArticleRepository articleRepository) {
		this.commentRepo = commentRepo;
		this.articleRepo = articleRepository;
	}
	
	@Transactional
	public Optional<CommentData> createComment(String slug, String body, User currentUser) {
		CommentData commentData = null;
		
		Optional<Article> findBySlug = articleRepo.findBySlug(slug);
		if(findBySlug.isPresent()) {
			Long articleId = findBySlug.get().getId();
			Long userId = currentUser.getId();
			
			Comment comment = Comment.builder()
					.body(body).articleId(articleId).userId(userId).build();
			
			Comment saved = commentRepo.save(comment);

			Long id = saved.getId();
			commentData = commentRepo.findCommentDataById(id, userId).get();
		}
		
		return Optional.ofNullable(commentData);
	}

	public boolean deleteComment(String slug, Long id, User currentUser) {	
		boolean deleted = false;
		
		Optional<Article> findBySlug = articleRepo.findBySlug(slug);		
		if(findBySlug.isPresent()) {
			Long articleId = findBySlug.get().getId();
			Optional<Comment> optional = commentRepo.findByArticleAndId(articleId, id);
			if(optional.isPresent()) {
				commentRepo.delete(optional.get());
				deleted = true;
			}
		}
		return deleted;
	}
	
}
