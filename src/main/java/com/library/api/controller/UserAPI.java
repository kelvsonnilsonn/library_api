package com.library.api.controller;

import com.library.api.command.user.UpdateUsernameCommand;
import com.library.api.util.HttpConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name="Usuários", description = "Gestão de usuários")
public interface UserAPI {

    @Operation(summary="Atualizar nome", description = "Atualiza o nome de um usuário")
    @ApiResponse(responseCode = HttpConstants.OK, description = "Nome atualizado com sucesso")
    @ApiResponse(responseCode = HttpConstants.BAD_REQUEST, description = HttpConstants.BAD_REQUEST_MSG)
    @ApiResponse(responseCode = HttpConstants.SERVER_ERROR, description = HttpConstants.INTERN_SERVER_ERROR_MSG)
    ResponseEntity<Void> updateName(@RequestBody UpdateUsernameCommand command);
}
