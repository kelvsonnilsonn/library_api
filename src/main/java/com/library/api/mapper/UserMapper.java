package com.library.api.mapper;

import com.library.api.dto.UserResponseDTO;
import com.library.api.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO toResponse(User user);
}
