package com.studio.harbour.jdbc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studio.harbour.jdbc.domain.Tag;
import com.studio.harbour.jdbc.json.TagsData;
import com.studio.harbour.jdbc.repository.TagRepository;

@Service
public class TagService {
	private TagRepository tagRepo;
	
	@Autowired
	public TagService(TagRepository tagRepo) {
		this.tagRepo = tagRepo;
	}
	
	public TagsData getTags() {		
		List<String> list = new ArrayList<String>();
		
		Iterable<Tag> all = tagRepo.findAll();		
		all.forEach(tag -> 
						list.add(tag.getName()));
		
		TagsData tagsData = TagsData.builder().list(list).build();
		return tagsData;
	}
	
}
