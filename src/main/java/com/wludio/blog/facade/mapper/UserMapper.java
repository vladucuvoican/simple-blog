package com.wludio.blog.facade.mapper;

import com.wludio.blog.entites.User;
import com.wludio.blog.facade.dto.UserDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class UserMapper {

    public abstract UserDto toDto(User user);
    public abstract List<UserDto> toDtos(List<User> userList);

    public abstract User toEntity(UserDto userDto);
    public abstract List<User> toEntities(List<UserDto> userDtoList);
}
