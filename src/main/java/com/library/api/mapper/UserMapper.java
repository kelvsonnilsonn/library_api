package com.library.api.mapper;

import com.library.api.dto.UserRequestDTO;
import com.library.api.dto.UserResponseDTO;
import com.library.api.model.User;

public class UserMapper {
    public static User dtoToUser(UserRequestDTO dto){
        return new User(dto.username(), dto.password());
    }

    public static UserResponseDTO toResponse(User user){
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getCreatedAt());
    }
}
