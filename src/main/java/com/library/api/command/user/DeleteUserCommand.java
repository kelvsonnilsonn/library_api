package com.library.api.command.user;

public record DeleteUserCommand(
        Long userId,
        String reason
) {
}
