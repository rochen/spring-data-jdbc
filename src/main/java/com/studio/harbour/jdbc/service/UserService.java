package com.studio.harbour.jdbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studio.harbour.jdbc.domain.User;
import com.studio.harbour.jdbc.repository.UserRepository;

@Service
public class UserService {
	private UserRepository userRepo;
		
	@Autowired
	public UserService(UserRepository userRepo) {
		this.userRepo = userRepo;		
	}
	
	public User save(User user) {
		User saved = userRepo.save(user);
		return saved;
	}
}
