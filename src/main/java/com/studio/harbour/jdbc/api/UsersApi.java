package com.studio.harbour.jdbc.api;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.studio.harbour.jdbc.domain.User;
import com.studio.harbour.jdbc.service.UserService;

import lombok.Getter;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping(path = "/users")
public class UsersApi {
	private UserService userService;
	
	@Autowired
	public UsersApi(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping
	public ResponseEntity<User> registration(@RequestBody RegisterParam registerParam) {
		User user = User.builder().email(registerParam.getEmail())
					  			  .username(registerParam.getUsername())
					  			  .password(registerParam.getPassword()).build();
	    User saved = userService.save(user);   
		return ResponseEntity.status(201).body(saved);
	}
}

@Getter
@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT ,use = JsonTypeInfo.Id.NAME)
@NoArgsConstructor
class RegisterParam {
    @NotBlank(message = "can't be empty")
    @Email(message = "should be an email")
    private String email;
    @NotBlank(message = "can't be empty")
    private String username;
    @NotBlank(message = "can't be empty")
    private String password;
}