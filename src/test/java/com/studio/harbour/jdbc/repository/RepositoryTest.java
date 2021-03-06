package com.studio.harbour.jdbc.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.studio.harbour.jdbc.domain.Article;
import com.studio.harbour.jdbc.domain.Comment;
import com.studio.harbour.jdbc.domain.Tag;
import com.studio.harbour.jdbc.domain.User;
import com.studio.harbour.jdbc.json.CommentData;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class RepositoryTest {
	@Autowired
	UserRepository userRepo;

	@Autowired
	ArticleRepository articleRepo;
	
	@Autowired
	TagRepository tagRepo;
	
	@Autowired
	CommentRepository commentRepo;

	
	@Test
	@Order(1)
	public void createUser() {
		User u = new User();
		u.setEmail("new@example.com");
		u.setUsername("new");		
		u.setBio(null);
		
		User saved = userRepo.save(u);

		Long id = saved.getId();
		assertThat(id).isNotNull();

		Optional<User> reloaded = userRepo.findById(id);
		assertThat(reloaded).isNotEmpty();
		assertThat(reloaded.get().getUsername()).isEqualTo("new");
	}
	
	@Test
	@Order(2)
	public void createFollow() {
		Optional<User> optional = userRepo.findById(1L);
		User self = optional.get();

		User objective = new User();
		objective.setEmail("objective@example.com");
		objective.setUsername("objective");		
		objective = userRepo.save(objective);

		User objective2 = new User();
		objective2.setEmail("objective2@example.com");
		objective2.setUsername("objective2");		
		objective2 = userRepo.save(objective2);

		// follow
		self.follow(objective);
		self.follow(objective2);
		self = userRepo.save(self);
		assertThat(self.getFollows().size()).isEqualTo(3);
		
		// unfollow
		self.unfollow(objective2);
		self = userRepo.save(self);
		assertThat(self.getFollows().size()).isEqualTo(2);

	}
	
	@Test
	@Order(3)
	public void createArticleWithUser() {
		Optional<User> optional = userRepo.findById(1L);
		User tester = optional.get();

		Article article = new Article();
		article.setSlug("slug");
		article.setTitle("article test");
		article.setBody("中文");
		article.setUserId(tester.getId());
		
		Article saved = articleRepo.save(article);
		
		Long id = saved.getId();
		assertThat(id).isNotNull();
		
		Optional<Article> reloaded = articleRepo.findById(id);
		assertThat(reloaded).isNotEmpty();
		assertThat(reloaded.get().getSlug()).isEqualTo("slug");
	}
	
	@Test
	@Order(4)
	public void createTag() {		
		Optional<Article> optional = articleRepo.findById(1L);
		Article article = optional.get();
		
		// create tag
		Tag tag = new Tag();
		tag.setName("travel");
		tagRepo.save(tag);
		
		// add tag to article
		article.tag(tag);
		Article saved = articleRepo.save(article);
		assertThat(saved.getTags()).isNotEmpty();
	}
	
	@Test
	@Order(5)
	public void createComment() {		
		Optional<Article> articleOpt = articleRepo.findById(1L);
		Article article = articleOpt.get();
		Long articleId = article.getId();
		
		Optional<User> userOpt = userRepo.findById(1L);
		User user = userOpt.get();
		Long userId = user.getId();

		// create comment
		Comment comment = new Comment();
		comment.setBody("this is a comment");
		comment.setArticleId(articleId);
		comment.setUserId(userId);
		Comment saved = commentRepo.save(comment);

		assertThat(saved.getId()).isNotNull();
	}

	@Test @Order(6)
	public void getCommentData() {
		Optional<CommentData> optional = commentRepo.findCommentDataById(1L, 1L);
		assertThat(optional).isPresent();
		CommentData commentData = optional.get();
		
		assertThat(commentData.getProfileData()).isNotNull();
	}
}
