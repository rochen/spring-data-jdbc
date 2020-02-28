package com.studio.harbour.jdbc.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studio.harbour.jdbc.json.UserData;
import com.studio.harbour.jdbc.security.JwtService;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class CommentsApiTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	protected ObjectMapper objectMapper;
	
	@Autowired
	JwtService jwtService;

	@Test
	public void createComment() throws Exception {
		String authorization = getAuthorization("robinly");
		
		String slug = "tunisia-is-nice";
		
		NewCommentParam param = NewCommentParam.builder()
				.body("this is a comment").build();
		
		String content = objectMapper.writeValueAsString(param);
		
		mockMvc.perform(post("/articles/{slug}/comments", slug)
				.header("Authorization", authorization)
				.contentType(MediaType.APPLICATION_JSON).content(content))
				.andDo(print())
				.andExpect(status().isCreated());
	}
	
	@Test
	public void deleteComment() throws Exception {
		String authorization = getAuthorization("robinly");
		
		String slug = "tunisia-is-nice";
				
		mockMvc.perform(delete("/articles/{slug}/comments/{id}", slug, 1L)
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
