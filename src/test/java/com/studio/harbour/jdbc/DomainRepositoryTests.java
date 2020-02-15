package com.studio.harbour.jdbc;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.studio.harbour.jdbc.domain.Article;
import com.studio.harbour.jdbc.domain.Tag;
import com.studio.harbour.jdbc.domain.User;
import com.studio.harbour.jdbc.repository.ArticleRepository;
import com.studio.harbour.jdbc.repository.TagRepository;
import com.studio.harbour.jdbc.repository.UserRepository;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class DomainRepositoryTests {
	@Autowired
	UserRepository userRepo;

	@Autowired
	ArticleRepository articleRepo;
	
	@Autowired
	TagRepository tagRepo;
	

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
		assertThat(self.getFollows().size()).isEqualTo(2);
		
		// unfollow
		self.unfollow(objective2);
		self = userRepo.save(self);
		assertThat(self.getFollows().size()).isEqualTo(1);

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

}
