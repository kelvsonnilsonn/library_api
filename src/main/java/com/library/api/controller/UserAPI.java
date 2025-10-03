package com.library.api.controller;

import com.library.api.dto.PageResponseDTO;
import com.library.api.dto.UserResponseDTO;
import com.library.api.dto.UserUpdateDto;
import com.library.api.util.HttpConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name="Usuários", description = "Gestão de usuários")
public interface UserAPI {

    @Operation(summary = "Deletar um usuário", description = "Deleta um usuário")
    @ApiResponse(responseCode = HttpConstants.CREATED, description = "Usuário deletado com sucesso")
    @ApiResponse(responseCode = HttpConstants.NOT_FOUND, description = HttpConstants.NOT_FOUND_MSG)
    @ApiResponse(responseCode = HttpConstants.SERVER_ERROR, description = HttpConstants.INTERN_SERVER_ERROR_MSG)
    ResponseEntity<String> delete(@PathVariable Long id);

    @Operation(summary = "Procurar um usuário", description = "Procura um usuário")
    @ApiResponse(responseCode = HttpConstants.OK, description = "Usuário encontrado com sucesso")
    @ApiResponse(responseCode = HttpConstants.NOT_FOUND, description = HttpConstants.NOT_FOUND_MSG)
    @ApiResponse(responseCode = HttpConstants.SERVER_ERROR, description = HttpConstants.INTERN_SERVER_ERROR_MSG)
    ResponseEntity<UserResponseDTO> findById(@PathVariable Long id);

    @Operation(summary = "Listar usuários", description = "Lista todos os usuários")
    @ApiResponse(responseCode = HttpConstants.OK, description = "usuários listados com sucesso")
    @ApiResponse(responseCode = HttpConstants.SERVER_ERROR, description = HttpConstants.INTERN_SERVER_ERROR_MSG)
    ResponseEntity<PageResponseDTO<UserResponseDTO>> findAll(Pageable pageable);

    @Operation(summary="Procurar usuário por nome", description = "Procura um usuário pelo nome")
    @ApiResponse(responseCode = HttpConstants.OK, description = "Usuário encontrado com sucesso")
    @ApiResponse(responseCode = HttpConstants.NOT_FOUND, description = HttpConstants.NOT_FOUND_MSG)
    @ApiResponse(responseCode = HttpConstants.SERVER_ERROR, description = HttpConstants.INTERN_SERVER_ERROR_MSG)
    ResponseEntity<UserResponseDTO> findByUsername(@RequestParam String name);

    @Operation(summary="Atualizar nome", description = "Atualiza o nome de um usuário")
    @ApiResponse(responseCode = HttpConstants.OK, description = "Nome atualizado com sucesso")
    @ApiResponse(responseCode = HttpConstants.BAD_REQUEST, description = HttpConstants.BAD_REQUEST_MSG)
    @ApiResponse(responseCode = HttpConstants.SERVER_ERROR, description = HttpConstants.INTERN_SERVER_ERROR_MSG)
    ResponseEntity<UserResponseDTO> updateName(@RequestBody UserUpdateDto updateDto);
}
