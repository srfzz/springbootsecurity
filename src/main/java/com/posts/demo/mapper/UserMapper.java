package com.posts.demo.mapper;

import com.posts.demo.dto.UserDto;
import com.posts.demo.entities.UserEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(UserEntity userEntity)
    {
    return  UserDto.builder().id(userEntity.getId()).name(userEntity.getName()).email(userEntity.getEmail()).roles(userEntity.getRoles()).permissions(userEntity.getPermissions()).build();
    }
    public UserEntity toEntity(UserDto userDto)
    {
        return UserEntity.builder().name(userDto.name()).email(userDto.email()).password(userDto.password()).roles(userDto.roles()).permissions(userDto.permissions()).build();
    }
}
