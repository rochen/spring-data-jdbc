package com.studio.harbour.jdbc.api;

import lombok.Data;

@Data
public class UserData {
	private String email;
	private String token;
	private String username;
	private String bio;
	private String image;
}
