package com.studio.harbour.jdbc.api;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersApiTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	protected ObjectMapper objectMapper;
	
	@Test
	public void registration() throws Exception {		
		RegisterParam param = RegisterParam.builder()
				.email("jake@jake.jake").username("jacob").password("jakejake").build();
		
		String content = objectMapper.writeValueAsString(param);
		
		mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(content))
			   .andDo(print())
			   .andExpect(status().isCreated())
			   .andExpect(content().string(containsString("token")));
	}
	
	@Test
	public void login() throws Exception {		
		LoginParam param = LoginParam.builder()
				.email("jake@jake.jake").password("jakejake").build();
		
		String content = objectMapper.writeValueAsString(param);
		
		mockMvc.perform(post("/users/login").contentType(MediaType.APPLICATION_JSON).content(content))
			   .andDo(print())
			   .andExpect(status().isOk())
			   .andExpect(content().string(containsString("token")));
	}
}
