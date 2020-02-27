
package com.studio.harbour.jdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.studio.harbour.jdbc.json.ArticleData;
import com.studio.harbour.jdbc.json.ProfileData;

public class ArticleResultSetExtractor implements ResultSetExtractor<ArticleData> {

	@Override
	public ArticleData extractData(ResultSet rs) throws SQLException, DataAccessException {
		ArticleData articleData = null;
		
		if(rs.next()) {
			String slug = rs.getString("slug");
			String title = rs.getString("title");
			String description = rs.getString("description");
			String body = rs.getString("body");
			LocalDateTime createdAt = rs.getObject("created_at", LocalDateTime.class);
			LocalDateTime updatedAt = rs.getObject("updated_at", LocalDateTime.class);
		    articleData = ArticleData.builder().slug(slug)
		    		.title(title).description(description)
		    		.body(body).createdAt(createdAt).updatedAt(updatedAt).build();
			
		    // profile
			String username = rs.getString("username");
			String bio = rs.getString("bio");
			String image = rs.getString("image");
			boolean following = rs.getBoolean("following");
			ProfileData profileData = ProfileData.builder().username(username)
					.bio(bio).image(image).following(following).build();
			articleData.setProfileData(profileData);
			
			// tag
			String tag = rs.getString("tag");
			if(tag != null)
				articleData.getTagList().add(tag);
			
			while(rs.next()) {
				tag = rs.getString("tag");
				articleData.getTagList().add(tag);
			}
		}
	
		return articleData;
	}

}
