package com.studio.harbour.jdbc.api;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.studio.harbour.jdbc.domain.User;
import com.studio.harbour.jdbc.json.UserData;
import com.studio.harbour.jdbc.service.UserService;

import lombok.Getter;

@RestController
public class UsersApi {
	private UserService userService;
	
	@Autowired
	public UsersApi(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping(path = "/users/login")
	public ResponseEntity<UserData> authentication(@Valid @RequestBody LoginParam loginParam) {
		String email = loginParam.getEmail();
		UserData userData = userService.findByEmail(email);
		return ResponseEntity.ok(userData);
	}
	
	@PostMapping(path = "/users")
	public ResponseEntity<UserData> registration(@Valid @RequestBody RegisterParam registerParam) {
		User user = User.builder().email(registerParam.getEmail())
					  			  .username(registerParam.getUsername())
					  			  .password(registerParam.getPassword()).build();
	    UserData saved = userService.save(user);   
		return ResponseEntity.status(201).body(saved);
	}
	
}

@Getter
@JsonRootName("user")
class LoginParam {
    @NotBlank(message = "can't be empty")
    @Email(message = "should be an email")
    private String email;
    @NotBlank(message = "can't be empty")
    private String password;
}

@Getter
@JsonRootName("user")
class RegisterParam {
    @NotBlank(message = "can't be empty")
    @Email(message = "should be an email")
    private String email;
    @NotBlank(message = "can't be empty")
    private String username;
    @NotBlank(message = "can't be empty")
    private String password;
}