package com.studio.harbour.jdbc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studio.harbour.jdbc.domain.User;
import com.studio.harbour.jdbc.json.ProfileData;
import com.studio.harbour.jdbc.repository.UserRepository;

@Service
public class ProfileService {
	private UserRepository userRepo;
		
	@Autowired
	public ProfileService(UserRepository userRepo) {
		this.userRepo = userRepo;		
	}
	
	public Optional<ProfileData> findByUsername(String username, User currentUser) {
		// current user might be null if no authentication
		Long id = currentUser == null? null: currentUser.getId();
		Optional<ProfileData> profileData = userRepo.findObjectByUsername(username, id);
		return profileData;
	}
	
	public Optional<ProfileData> followUser(String username, User currentUser) {
		Optional<User> object = userRepo.findByUsername(username);
		if(object.isPresent()) {
			currentUser.follow(object.get());
			userRepo.save(currentUser);
			return findByUsername(username, currentUser);
		} else {
			return Optional.empty();
		}
	}
	
	public Optional<ProfileData> unfollowUser(String username, User currentUser) {
		Optional<User> object = userRepo.findByUsername(username);
		if(object.isPresent()) {
			currentUser.unfollow(object.get());
			userRepo.save(currentUser);
			return findByUsername(username, currentUser);
		} else {
			return Optional.empty();
		}
	}
}
