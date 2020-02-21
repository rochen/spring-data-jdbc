package com.studio.harbour.jdbc.api;

import java.util.Optional;

import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.studio.harbour.jdbc.domain.User;
import com.studio.harbour.jdbc.json.UserData;
import com.studio.harbour.jdbc.service.UserService;

import lombok.Getter;

@RestController
@RequestMapping(path = "/user")
public class CurrentUserApi {
	private UserService userService;
	
	@Autowired
	public CurrentUserApi(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public ResponseEntity<UserData> getCurrentUser(User currentUser) {
		// TODO: using real user
		currentUser= userService.testCurrentUser();

		Long id = currentUser.getId();
		Optional<UserData> userData = userService.findById(id);
		
		return ResponseEntity.of(userData);
	}
	
	@PutMapping
	public ResponseEntity<UserData> updateUser(User currentUser, @RequestBody UpdateUserParam updateUserParam) {
		// TODO: using real user
		currentUser= userService.testCurrentUser();

		UserData userData = userService.update(currentUser, updateUserParam.getEmail(), 
				updateUserParam.getUsername(), updateUserParam.getPassword(), 
				updateUserParam.getBio(), updateUserParam.getImage());
		
		return ResponseEntity.ok(userData);
	}
}

@Getter
@JsonRootName("user")
class UpdateUserParam {
    @Email(message = "should be an email")
    private String email;
    private String password;
    private String username;
    private String bio;
    private String image;
}
