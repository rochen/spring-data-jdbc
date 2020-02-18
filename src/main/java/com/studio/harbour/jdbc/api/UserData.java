package com.studio.harbour.jdbc.api;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

@Data
@JsonRootName("user")
public class UserData {
	private String email;
	private String token;
	private String username;
	private String bio;
	private String image;
}
