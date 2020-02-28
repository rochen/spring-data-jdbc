package com.studio.harbour.jdbc.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studio.harbour.jdbc.json.ArticleData;
import com.studio.harbour.jdbc.json.UserData;
import com.studio.harbour.jdbc.security.JwtService;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class ArticlesApiTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	protected ObjectMapper objectMapper;
	
	@Autowired
	JwtService jwtService;

	@Test @Order(1)
	public void getArticle() throws Exception {
		String slug = "tunisia-is-nice";
		mockMvc.perform(get("/articles/{slug}", slug))
				.andDo(print())
				.andExpect(status().isOk());

	}

	@Test @Order(1)
	public void createArticle() throws Exception {
		String authorization = getAuthorization("robinly");
		
		NewArticleParam param = NewArticleParam.builder()
				.title("How to train your dragon")
				.description("Ever wonder how?")
				.body("You have to believe")
				.tagList(Arrays.array("reactjs", "angularjs", "dragons")).build();

		String content = objectMapper.writeValueAsString(param);

		mockMvc.perform(post("/articles").header("Authorization", authorization)
						.contentType(MediaType.APPLICATION_JSON).content(content))
				.andDo(print())
				.andExpect(status().isOk());

	}
	
	@Test @Order(2)
	public void updateArticle() throws Exception {
		String authorization = getAuthorization("robinly");
		
		UpdateArticleParam param = UpdateArticleParam.builder()
				.title("How to train your dragon 2")
				.description("Ever wonder how? common")
				.body("You have to believe, balbal").build();

		String content = objectMapper.writeValueAsString(param);

		MvcResult andReturn = mockMvc.perform(put("/articles/{slug}", "how-to-train-your-dragon")
						.header("Authorization", authorization)
						.contentType(MediaType.APPLICATION_JSON).content(content))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		
		String returnContent = andReturn.getResponse().getContentAsString();
		ArticleData articleData = objectMapper.readValue(returnContent, ArticleData.class);
		assertEquals("how-to-train-your-dragon-2", articleData.getSlug());

	}
	
	@Test @Order(3)
	public void deleteArticle() throws Exception {
		String authorization = getAuthorization("robinly");

		mockMvc.perform(delete("/articles/{slug}", "how-to-train-your-dragon")
							.header("Authorization", authorization))
					.andDo(print())
					.andExpect(status().isNotFound());

		mockMvc.perform(delete("/articles/{slug}", "how-to-train-your-dragon-2")
							.header("Authorization", authorization))
					.andDo(print())
					.andExpect(status().isNoContent());
		
	}
	
	private String getAuthorization(String username) {
		UserData user = UserData.builder().username(username).build();
		user = jwtService.getUserWithToken(user);
		String token = user.getToken();
		String authorization = String.format("Token %s", token);
		return authorization;
	}
}
