package com.studio.harbour.jdbc.mapper;

import org.mapstruct.Mapper;

import com.studio.harbour.jdbc.domain.Article;
import com.studio.harbour.jdbc.json.ArticleData;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
	
	@Mapping(target = "favorited", ignore = true)
	@Mapping(target = "favoritesCount", ignore = true)
	@Mapping(target = "profileData", ignore = true)
	@Mapping(target = "tagList", ignore = true)
	ArticleData userToArticleData(Article article);
}
