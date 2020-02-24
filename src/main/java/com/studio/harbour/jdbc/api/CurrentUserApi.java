package com.studio.harbour.jdbc.api;

import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.studio.harbour.jdbc.domain.User;
import com.studio.harbour.jdbc.json.UserData;
import com.studio.harbour.jdbc.service.UserService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping(path = "/user")
public class CurrentUserApi {
	private UserService userService;
	
	@Autowired
	public CurrentUserApi(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public ResponseEntity<UserData> getCurrentUser(@AuthenticationPrincipal User currentUser,
			@RequestHeader(value = "Authorization") String authorization) {
		
		Long id = currentUser.getId();
		UserData userData = userService.findById(id).get();		
		userData = userService.getJwtToken(userData, authorization);
		
		return ResponseEntity.ok(userData);
	}
	
	@PutMapping
	public ResponseEntity<UserData> updateUser(@AuthenticationPrincipal User currentUser, 
			@RequestBody UpdateUserParam updateUserParam,
			@RequestHeader(value = "Authorization") String authorization) {
		
		UserData userData = userService.update(currentUser, updateUserParam.getEmail(), 
				updateUserParam.getUsername(), updateUserParam.getPassword(), 
				updateUserParam.getBio(), updateUserParam.getImage());

		userData = userService.getJwtToken(userData, authorization);

		return ResponseEntity.ok(userData);
	}
}

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("user")
class UpdateUserParam {
    @Email(message = "should be an email")
    private String email;
    private String password;
    private String username;
    private String bio;
    private String image;
}
