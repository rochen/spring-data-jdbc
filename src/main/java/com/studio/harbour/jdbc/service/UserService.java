package com.studio.harbour.jdbc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studio.harbour.jdbc.api.UserData;
import com.studio.harbour.jdbc.domain.User;
import com.studio.harbour.jdbc.mapper.UserMapper;
import com.studio.harbour.jdbc.repository.UserRepository;

@Service
public class UserService {
	private UserRepository userRepo;
	private UserMapper userMapper;
		
	@Autowired
	public UserService(UserRepository userRepo, UserMapper userMapper) {
		this.userRepo = userRepo;		
		this.userMapper = userMapper;
	}
	
	public UserData save(User user) {
		User saved = userRepo.save(user);
		UserData userData = userMapper.userToUserData(saved);
		return userData;
	}
	
	public UserData findByEmail(String email) {
		Optional<User> optional = userRepo.findByEmail(email);
		
		UserData userData = userMapper.userToUserData(optional.get());
		return userData;
	}
}
