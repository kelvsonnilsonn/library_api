package com.library.api.dto.auth;

import com.library.api.model.Password;

public record RegisterRequestDTO (String username, String password) {
}
