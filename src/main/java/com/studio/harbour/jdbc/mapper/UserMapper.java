package com.studio.harbour.jdbc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.studio.harbour.jdbc.api.UserData;
import com.studio.harbour.jdbc.domain.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	@Mapping(target = "token", ignore = true)
	UserData userToUserData(User user);
}
