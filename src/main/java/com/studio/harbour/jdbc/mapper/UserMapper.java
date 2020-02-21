
package com.studio.harbour.jdbc.mapper;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.studio.harbour.jdbc.domain.User;
import com.studio.harbour.jdbc.json.UserData;

@Mapper(componentModel = "spring")
public interface UserMapper {

	default Optional<UserData> user2Data(Optional<User> optional) {
		User user = optional.get();
		UserData userData = entityToData(user);
		return Optional.ofNullable(userData);
	}
	
	@Mapping(target = "token", ignore = true)
	UserData entityToData(User user);
}
