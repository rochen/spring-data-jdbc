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
	
	public Optional<ProfileData> findByUsername(String username, User user) {
		Long id = user == null? null: user.getId();
		Optional<ProfileData> profileData = userRepo.findObjectByUsername(username, id);
		return profileData;
	}
}
