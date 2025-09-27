package com.library.api.controller.api;

import com.library.api.dto.UserRequestDTO;
import com.library.api.dto.UserResponseDTO;
import com.library.api.util.HttpConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name="Usuários", description = "Gestão de usuários")
public interface UserAPI {

    @Operation(summary = "Criar um usuário", description = "Cria um novo usuário")
    @ApiResponse(responseCode = HttpConstants.CREATED, description = "Usuário criado com sucesso")
    @ApiResponse(responseCode = HttpConstants.BAD_REQUEST, description = HttpConstants.BAD_REQUEST_MSG)
    @ApiResponse(responseCode = HttpConstants.CONFLICT, description = "O nome de usuário inserido já existe")
    @ApiResponse(responseCode = HttpConstants.SERVER_ERROR, description = HttpConstants.INTERN_SERVER_ERROR_MSG)
    ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO userRequestDTO);

    @Operation(summary = "Deletar um usuário", description = "Deleta um usuário")
    @ApiResponse(responseCode = HttpConstants.CREATED, description = "Usuário deletado com sucesso")
    @ApiResponse(responseCode = HttpConstants.NOT_FOUND, description = HttpConstants.NOT_FOUND_MSG)
    @ApiResponse(responseCode = HttpConstants.SERVER_ERROR, description = HttpConstants.INTERN_SERVER_ERROR_MSG)
    ResponseEntity<String> delete(@RequestParam Long id);

    @Operation(summary = "Procurar um usuário", description = "Procura um usuário")
    @ApiResponse(responseCode = HttpConstants.CREATED, description = "Usuário encontrado com sucesso")
    @ApiResponse(responseCode = HttpConstants.NOT_FOUND, description = HttpConstants.NOT_FOUND_MSG)
    @ApiResponse(responseCode = HttpConstants.SERVER_ERROR, description = HttpConstants.INTERN_SERVER_ERROR_MSG)
    ResponseEntity<UserResponseDTO> findById(@RequestParam Long id);
}
