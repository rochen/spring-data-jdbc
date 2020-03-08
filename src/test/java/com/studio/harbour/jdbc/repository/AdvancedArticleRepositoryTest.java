package com.studio.harbour.jdbc.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.studio.harbour.jdbc.json.ArticleData;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class AdvancedArticleRepositoryTest {
	@Autowired
	ArticleRepository articleRepo;
		
	@Test
	public void dynamicQueryTag() {
		String tag = null;
		String author = "robinly";
		String favoritedBy = null;
		Pageable page = PageRequest.of(0, 5);
		List<Long> findByTag = articleRepo.findArticles(tag, author, favoritedBy, page);		

		for(Long id: findByTag) {
			System.out.println("id:" + id);
		}

		assertThat(findByTag).containsOnly(1L);
	}
	
	@Test
	public void fetchArticles() {
		List<Long> idList = Arrays.asList(9L,13L,8L,10L,12L);
		Long userId = 2L;
		
		List<ArticleData> fetchArticles = articleRepo.fetchArticles(idList, userId );
		for(ArticleData data: fetchArticles) {
			System.out.println(data);
		}
		
	}

}
