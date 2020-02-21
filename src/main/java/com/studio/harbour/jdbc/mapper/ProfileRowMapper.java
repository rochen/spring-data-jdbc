package com.studio.harbour.jdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.studio.harbour.jdbc.json.ProfileData;

public class ProfileRowMapper implements RowMapper<ProfileData>{

	@Override
	public ProfileData mapRow(ResultSet rs, int rowNum) throws SQLException {		
		String username = rs.getString("username");
		String bio = rs.getString("bio");
		String image = rs.getString("image");
		Object follow = rs.getObject("follow");
		Boolean following = follow == null? false: true;
		
		ProfileData profileData = ProfileData.builder()
									.username(username)
									.bio(bio)
									.image(image)
									.following(following).build();
		
		return profileData;
	}

}
