package com.studio.harbour.jdbc.api;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.studio.harbour.jdbc.domain.User;
import com.studio.harbour.jdbc.json.CommentData;
import com.studio.harbour.jdbc.service.CommentService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping(path = "/articles/{slug}/comments")
public class CommentsApi {
	private CommentService commentService;
	
	@Autowired
	public CommentsApi(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@PostMapping
	public ResponseEntity<CommentData> createComment(@PathVariable("slug") String slug,
            								   @AuthenticationPrincipal User currentUser,
            								   @Valid @RequestBody NewCommentParam newCommentParam) {
		
		String body = newCommentParam.getBody();
		Optional<CommentData> optional = commentService.createComment(slug, body, currentUser);
		
		return optional.isPresent()?
				ResponseEntity.status(201).body(optional.get()): 
				ResponseEntity.notFound().build();
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteComment(@PathVariable("slug") String slug, @PathVariable("id") Long id,
										   @AuthenticationPrincipal User currentUser) {
		
		boolean deleted = commentService.deleteComment(slug, id, currentUser);
		return deleted? ResponseEntity.noContent().build(): ResponseEntity.notFound().build();
	}
}

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName("comment")
class NewCommentParam {
    @NotBlank(message = "can't be empty")
    private String body;
}