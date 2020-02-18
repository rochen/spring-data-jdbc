package com.studio.harbour.jdbc.mapper;

import org.mapstruct.Mapper;

import com.studio.harbour.jdbc.api.ProfileData;
import com.studio.harbour.jdbc.domain.User;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
	
	@Mapping(target = "following", ignore = true)
	ProfileData userToProfileData(User user);
}
