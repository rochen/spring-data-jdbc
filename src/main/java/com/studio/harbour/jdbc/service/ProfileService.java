package com.studio.harbour.jdbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studio.harbour.jdbc.api.ProfileData;
import com.studio.harbour.jdbc.domain.User;
import com.studio.harbour.jdbc.mapper.ProfileMapper;
import com.studio.harbour.jdbc.repository.UserRepository;

@Service
public class ProfileService {
	private UserRepository userRepo;
	private ProfileMapper profileMapper;
		
	@Autowired
	public ProfileService(UserRepository userRepo, ProfileMapper profileMapper) {
		this.userRepo = userRepo;		
		this.profileMapper = profileMapper;
	}
	
	public ProfileData findByUsername(String username) {
		User user = userRepo.findByUsername(username);
		ProfileData profileData = profileMapper.userToProfileData(user);
		return profileData;
	}
}
