package com.studio.harbour.jdbc.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studio.harbour.jdbc.json.TagsData;
import com.studio.harbour.jdbc.service.TagService;

@RestController
@RequestMapping(path = "/tags")
public class TagsApi {
	private TagService tagService;
	
	@Autowired
	public TagsApi(TagService tagService) {
		this.tagService = tagService;
	}
	
	
	@GetMapping
	public ResponseEntity<?> getTags() {
		TagsData tags = tagService.getTags();
		return ResponseEntity.ok(tags);
	}
	
}