package com.studio.harbour.jdbc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.studio.harbour.jdbc.domain.User;
import com.studio.harbour.jdbc.json.UserData;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	@Mapping(target = "token", ignore = true)
	UserData userToUserData(User user);
}
