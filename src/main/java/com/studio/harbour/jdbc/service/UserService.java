package com.studio.harbour.jdbc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studio.harbour.jdbc.domain.User;
import com.studio.harbour.jdbc.json.UserData;
import com.studio.harbour.jdbc.repository.UserRepository;

@Service
public class UserService {
	private UserRepository userRepo;
		
	@Autowired
	public UserService(UserRepository userRepo) {
		this.userRepo = userRepo;		
	}
	
	public UserData save(User user) {
		User saved = userRepo.save(user);
		UserData userData = UserData.builder()
						.email(saved.getEmail())
						.username(saved.getUsername())
						.bio(saved.getBio())
						.image(saved.getImage()).build();
						  
		return userData;
	}
	
	public Optional<UserData> findByEmail(String email) {
		Optional<UserData> optional = userRepo.findByEmail(email);		
		return optional;
	}
}
