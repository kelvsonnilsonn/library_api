package com.library.api.command.user;

public record UpdateUsernameCommand(
        String newName
) {
}
