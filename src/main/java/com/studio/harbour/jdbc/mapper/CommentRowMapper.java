package com.studio.harbour.jdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import com.studio.harbour.jdbc.json.CommentData;
import com.studio.harbour.jdbc.json.ProfileData;

public class CommentRowMapper implements RowMapper<CommentData>{

	@Override
	public CommentData mapRow(ResultSet rs, int rowNum) throws SQLException {
		String username = rs.getString("username");
		String bio = rs.getString("bio");
		String image = rs.getString("image");
		Object follow = rs.getObject("follow");
		Boolean following = follow == null? false: true;
		
		ProfileData profileData = ProfileData.builder()
				.username(username).bio(bio).image(image).following(following).build();
		
		long id = rs.getLong("id");
		String body = rs.getString("body");
		LocalDateTime createdAt = rs.getObject("created_at", LocalDateTime.class);
		LocalDateTime updatedAt = rs.getObject("updated_at", LocalDateTime.class);
		
		CommentData commentData = CommentData.builder()
			.id(id).body(body).createdAt(createdAt).updatedAt(updatedAt)
			.profileData(profileData).build();
		return commentData;
	}

}
