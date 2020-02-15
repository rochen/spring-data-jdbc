package com.studio.harbour.jdbc.domain;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Tag {
	@Id
    Long id;
	
    private String name;
}
