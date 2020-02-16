package com.studio.harbour.jdbc.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Comment {
	@Id
	private Long id;	
	private Long userId;
    private Long articleId;
    private String body;
		
	@CreatedDate
	private LocalDateTime createdAt;
}
