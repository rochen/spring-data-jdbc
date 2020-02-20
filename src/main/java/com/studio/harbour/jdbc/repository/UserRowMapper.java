package com.studio.harbour.jdbc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.studio.harbour.jdbc.json.UserData;

public class UserRowMapper implements RowMapper<UserData>{

	@Override
	public UserData mapRow(ResultSet rs, int rowNum) throws SQLException {		
		String email = rs.getString("email");
		String username = rs.getString("username");
		String bio = rs.getString("bio");
		String image = rs.getString("image");
		
		UserData userData = UserData.builder()
								.email(email)
								.username(username)
								.bio(bio)
								.image(image).build();
		return userData;
	}

}
