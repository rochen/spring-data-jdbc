package com.studio.harbour.jdbc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.studio.harbour.jdbc.json.ArticleData;
import com.studio.harbour.jdbc.json.ProfileData;

public class AdvancedArticleRepositoryImpl implements AdvancedArticleRepository {
	
	private NamedParameterJdbcTemplate jdbc;

	public AdvancedArticleRepositoryImpl(NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	
	@Override
	public List<Long> findArticles(String tag, String author, String favoritedBy, Pageable page) {
		String sql = "select distinct a.id, a.created_at from article a " +
			"left join (select article, name tag from article_tag, tag where tag=id) tag on tag.article = a.id " +
			"left join (select article, username favorited_by from article_favorite, user where user=id) fav on fav.article = a.id " +
			"left join user author on author.id = a.user_id where 1 = 1 ";
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(tag != null) {
			sql = sql + "and tag.tag = :tag ";
			map.put("tag", tag);
		}
		if(author != null) {
			sql = sql + "and author.username = :author ";
			map.put("author", author);
		}
		if(favoritedBy != null) {
			sql = sql + "and fav.favorited_by = :favoritedBy ";
			map.put("favoritedBy", favoritedBy);
		}
		
		sql = sql + "order by created_at desc limit :offset, :pageSize";
		map.put("offset", page.getOffset());
		map.put("pageSize", page.getPageSize());
		
		List<Long> idList = jdbc.query(sql, map, new RowMapper<Long>() {

			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("id");
			}
		});
			
			
		return idList;
	}

	@Override
	public List<ArticleData> fetchArticles(List<Long> idList, Long userId) {
		String sql = "SELECT a.id, a.slug, a.title, a.description, a.body, a.created_at, a.updated_at, "
				+ "u.id user_id, u.username, u.bio, u.image, t.name tag, f.follow following, af.user favoriter FROM ARTICLE a "
				+ "join user u on u.id = a.user_id "
				+ "left join article_tag at on at.article = a.id "
				+ "left join article_favorite af on af.article =  a.id "
				+ "left join tag t on t.id = at.tag "
				+ "left join follow_ref f on f.follow = a.user_id and f.user = :user "
				+ "where a.id in (:idList)";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idList", idList);
		map.put("user", userId);
				
		List<ArticleData> query = jdbc.query(sql, map, new ResultSetExtractor<List<ArticleData>>() {

			@Override
			public List<ArticleData> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<ArticleData> list = new ArrayList<ArticleData>();
				long currentId = 0;
				
				ArticleData articleData = null;
				Set<Long> favorites = new HashSet<Long>();
				
				while(rs.next()) {
					long id = rs.getLong("id");
					
					if(id != currentId) {
						// favorites
						if(articleData != null) {
							articleData.setFavoritesCount(favorites.size());
							favorites = new HashSet<Long>();
						}
						
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
					    Long userId = rs.getObject("user_id", Long.class);
						String username = rs.getString("username");
						String bio = rs.getString("bio");
						String image = rs.getString("image");
						boolean following = rs.getBoolean("following");
						ProfileData profileData = ProfileData.builder().username(username)
								.bio(bio).image(image).following(following).build();
						articleData.setProfileData(profileData);
						
						// handle first favorite
						Long favoriter = rs.getObject("favoriter", Long.class);
						if(favoriter != null) {
							favorites.add(favoriter);
							if(favoriter.equals(userId)) {
								articleData.setFavorited(true);
							}				
						}
						
						// handle first tag
						String tag = rs.getString("tag");
						if(tag != null)
							articleData.getTagList().add(tag);
						
						list.add(articleData);
						currentId = id;
					} else {
						// handle rest favorite
						Long favoriter = rs.getObject("favoriter", Long.class);
						if(favoriter != null) {
							favorites.add(favoriter);
							if(favoriter.equals(userId)) {
								articleData.setFavorited(true);
							}				
						}

						// handle rest tag
						String tag = rs.getString("tag");
						if(tag != null) 
							articleData.getTagList().add(tag);
					}
				}
				return list;
			}
		});
			
			
		return query;
	}

}
