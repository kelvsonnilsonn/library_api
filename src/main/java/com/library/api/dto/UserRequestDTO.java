package com.library.api.dto;

import com.library.api.model.Password;

public record UserRequestDTO(
        String username,
        Password password
) {
}
