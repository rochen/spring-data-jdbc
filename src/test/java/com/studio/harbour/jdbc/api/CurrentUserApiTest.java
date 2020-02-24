package com.studio.harbour.jdbc.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
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
public class CurrentUserApiTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	protected ObjectMapper objectMapper;
	
	@Autowired
	JwtService jwtService;

	@Test
	public void getCurrentUser() throws Exception {
		String authorization = getAuthorization("robinly");
		
		mockMvc.perform(get("/user").header("Authorization", authorization))
				.andDo(print())
				.andExpect(status().isOk());

	}

	@Test
	public void updateProfile() throws Exception {
		String authorization = getAuthorization("robinly");
		
		UpdateUserParam param = UpdateUserParam.builder()
				.email("jake@update.test").bio("bio").password("pwd").image("img").build();

		String content = objectMapper.writeValueAsString(param);
		
		mockMvc.perform(put("/user").contentType(MediaType.APPLICATION_JSON).content(content)
				.header("Authorization", authorization))
				.andDo(print())
				.andExpect(status().isOk());

	}

	private String getAuthorization(String username) {
		UserData user = UserData.builder().username(username).build();
		user = jwtService.getUserWithToken(user);
		String token = user.getToken();
		String authorization = String.format("Token %s", token);
		return authorization;
	}

}
