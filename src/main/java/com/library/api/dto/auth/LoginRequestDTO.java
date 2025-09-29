package com.library.api.dto.auth;

import com.library.api.model.Password;

public record LoginRequestDTO(String username, Password password) {
}
