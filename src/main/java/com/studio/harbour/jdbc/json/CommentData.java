package com.studio.harbour.jdbc.json;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName( value = "comment")
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