package com.studio.harbour.jdbc.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.util.Assert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {
	@Id
	private Long id;
	
	private Long userId;
	private String slug;
	private String title;
	private String description;
	private String body;
		
	@CreatedDate
	private LocalDateTime createdAt;
	
	@LastModifiedDate
	private LocalDateTime updatedAt;
	
	// tag section
	
	@Default
	private List<ArticleTag> tags = new ArrayList<ArticleTag>();
	
	public void tag(Tag tag) {
		tags.add(createTag(tag));
	}
	
	public void untag(Tag untag) {
		for(ArticleTag at: tags) {
			if(at.tag == untag.id) {
				tags.remove(at);
				break;
			}
		}
	}
	
	private ArticleTag createTag(Tag tag) {
		Assert.notNull(tag, "Tag must not be null");
		Assert.notNull(tag.id, "Tag id must not be null");

		ArticleTag at = new ArticleTag();
		at.tag = tag.id;
		return at;
	}
	
	// favorites section
	
	@Default
	private Set<ArticleFavorite> favorites = new HashSet<ArticleFavorite>();

	public void like(User reader) {
		favorites.add(createFavorite(reader));
	}
	
	public void unlike(User reader) {
		for(ArticleFavorite ref: favorites) {
			if(ref.user == reader.id) {
				favorites.remove(ref);
				break;
			}
		}
	}

	private ArticleFavorite createFavorite(User reader) {
		Assert.notNull(reader, "User must not be null");
		Assert.notNull(reader.id, "User id must not be null");

		ArticleFavorite favorite = new ArticleFavorite();
		favorite.user = reader.id;
		return favorite;
	}
}
