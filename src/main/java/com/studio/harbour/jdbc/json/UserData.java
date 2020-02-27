package com.studio.harbour.jdbc.json;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName("user")
public class UserData {
	private String email;
	private String token;
	private String username;
	private String bio;
	private String image;
}
