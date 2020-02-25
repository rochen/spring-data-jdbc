package com.studio.harbour.jdbc.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	public ResponseEntity<ProfileData> getProfile(@PathVariable("username") String username,
												  @AuthenticationPrincipal User currentUser) {
		
		Optional<ProfileData> profileData = profileService.findByUsername(username, currentUser);		
		return ResponseEntity.of(profileData);
	}
	
	@PostMapping(path = "/follow")
	public ResponseEntity<ProfileData> followUser(@PathVariable("username") String username,
												  @AuthenticationPrincipal User currentUser) {
		
		Optional<ProfileData> profileData = profileService.followUser(username, currentUser);		
		return ResponseEntity.of(profileData);
	}
	
	@DeleteMapping(path = "/follow")
	public ResponseEntity<ProfileData> unfollowUser(@PathVariable("username") String username,
												  @AuthenticationPrincipal User currentUser) {
		
		Optional<ProfileData> profileData = profileService.unfollowUser(username, currentUser);		
		return ResponseEntity.of(profileData);
	}
	
}
