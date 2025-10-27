package com.example.zoo.web.mappers;

import com.example.zoo.domain.user.User;
import com.example.zoo.web.dto.user.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto userDto);

}
