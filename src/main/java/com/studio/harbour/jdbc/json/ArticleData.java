package com.studio.harbour.jdbc.json;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

@Data
@JsonRootName("article")
public class ArticleData {
	@JsonIgnore
	private String id;
	
	private String slug;
	private String title;
	private String description;
	private String body;
	private List<String> tagList;	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private boolean favorited;
	private int favoritesCount;
	@JsonProperty("author")
	private ProfileData profileData;
}
