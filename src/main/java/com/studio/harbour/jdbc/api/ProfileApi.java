package com.studio.harbour.jdbc.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studio.harbour.jdbc.domain.User;
import com.studio.harbour.jdbc.json.ProfileData;
import com.studio.harbour.jdbc.service.ProfileService;

@RestController
@RequestMapping(path = "/profiles/{username}")
public class ProfileApi {
	
	private ProfileService profileService;
	
	@Autowired
	public ProfileApi(ProfileService profileService) {
		this.profileService = profileService;
	}
	
	@GetMapping
	public ResponseEntity<ProfileData> getProfile(@PathVariable("username") String username) {
		User user = User.builder().id(1L).build();
		ProfileData profileData = profileService.findByUsername(username, user);
		return ResponseEntity.ok(profileData);
	}
	
}
