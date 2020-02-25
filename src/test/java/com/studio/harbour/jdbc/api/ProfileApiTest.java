package com.studio.harbour.jdbc.api;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studio.harbour.jdbc.json.ProfileData;
import com.studio.harbour.jdbc.json.UserData;
import com.studio.harbour.jdbc.security.JwtService;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfileApiTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	protected ObjectMapper objectMapper;
	
	@Autowired
	JwtService jwtService;
	
	@Test
	public void getProfileNotLogin() throws Exception {
		MvcResult andReturn = mockMvc.perform(get("/profiles/{username}", "yap"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("profile")))
				.andReturn();
		
		String content = andReturn.getResponse().getContentAsString();
		ProfileData profileData = objectMapper.readValue(content, ProfileData.class);
		
		assertFalse(profileData.isFollowing());
	}

	@Test
	public void getProfileFollowing() throws Exception {
		String authorization = getAuthorization("robinly");
		MvcResult andReturn = mockMvc.perform(get("/profiles/{username}", "yap").header("Authorization", authorization))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("profile")))
				.andReturn();
		
		String content = andReturn.getResponse().getContentAsString();
		ProfileData profileData = objectMapper.readValue(content, ProfileData.class);
		
		assertTrue(profileData.isFollowing());
	}

	@Test
	public void follow_unfollow() throws Exception {
		String authorization = getAuthorization("robinly");
		// unfollow
		MvcResult andReturn = mockMvc.perform(delete("/profiles/{username}/follow", "yap").header("Authorization", authorization))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("profile")))
				.andReturn();
		
		String content = andReturn.getResponse().getContentAsString();
		ProfileData profileData = objectMapper.readValue(content, ProfileData.class);
		
		assertFalse(profileData.isFollowing());
		
		// follow
		andReturn = mockMvc.perform(post("/profiles/{username}/follow", "yap").header("Authorization", authorization))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("profile")))
				.andReturn();
		
		content = andReturn.getResponse().getContentAsString();
		profileData = objectMapper.readValue(content, ProfileData.class);
		
		assertTrue(profileData.isFollowing());

	}

	private String getAuthorization(String username) {
		UserData user = UserData.builder().username(username).build();
		user = jwtService.getUserWithToken(user);
		String token = user.getToken();
		String authorization = String.format("Token %s", token);
		return authorization;
	}

}
