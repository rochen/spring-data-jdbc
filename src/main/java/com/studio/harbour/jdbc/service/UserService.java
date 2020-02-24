package com.studio.harbour.jdbc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.studio.harbour.jdbc.domain.User;
import com.studio.harbour.jdbc.json.UserData;
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

	public UserData register(User user) {
		User saved = userRepo.save(user);
		UserData userData = userMapper.entityToData(saved);
		return userData;
	}

	public Optional<UserData> findByEmail(String email) {
		Optional<User> user = userRepo.findByEmail(email);
		Optional<UserData> userData = userMapper.user2Data(user);
		return userData;
	}
	
	public Optional<UserData> findById(Long id) {
		Optional<User> user = userRepo.findById(id);
		Optional<UserData> userData = userMapper.user2Data(user);
		return userData;
	}
	
	public UserData update(User currentUser, String email, String username, String password, String bio, String image) {
		if(email != null) 
			currentUser.setEmail(email);

		if(username != null) 
			currentUser.setUsername(username);
		
		if(password != null) 
			currentUser.setPassword(password);
		
		if(bio != null) 
			currentUser.setBio(bio);
		
		if(image != null) 
			currentUser.setImage(image);
		
		User saved = userRepo.save(currentUser);
		
		UserData userData = userMapper.entityToData(saved);
		return userData;		
	}
	
    public UserData getJwtToken(UserData userData, String authorization) {
    	String[] strings = StringUtils.tokenizeToStringArray(authorization, " ");
    	String token = strings[1];
    	userData.setToken(token);
		return userData;
    }
    
}
