package com.studio.harbour.jdbc.json;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentData {
    private Long id;
    private String body;
    @JsonIgnore
    private String articleId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @JsonProperty("author")
    private ProfileData profileData;
}