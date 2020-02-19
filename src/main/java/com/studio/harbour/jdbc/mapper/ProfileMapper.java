package com.studio.harbour.jdbc.mapper;

import org.mapstruct.Mapper;

import com.studio.harbour.jdbc.domain.User;
import com.studio.harbour.jdbc.json.ProfileData;

import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
	
	@Mapping(target = "following", ignore = true)
	ProfileData userToProfileData(User user);
}
