package com.library.api.controller;

import com.library.api.dto.auth.LoginRequestDTO;
import com.library.api.dto.auth.RegisterRequestDTO;
import com.library.api.util.HttpConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Segurança", description = "Sistema de autenticação")
public interface AuthAPI {

    @Operation(summary = "Logar um usuário", description = "Loga de um usuário")
    @ApiResponse(responseCode = HttpConstants.OK, description = "Login realizado com sucesso")
    @ApiResponse(responseCode = HttpConstants.BAD_REQUEST, description = HttpConstants.BAD_REQUEST_MSG)
    ResponseEntity<?> login(@RequestBody LoginRequestDTO body);

    @Operation(summary = "Registrar um usuário", description = "Registra um usuário")
    @ApiResponse(responseCode = HttpConstants.CREATED, description = "Novo usuário criado com sucesso")
    @ApiResponse(responseCode = HttpConstants.BAD_REQUEST, description = HttpConstants.BAD_REQUEST_MSG)
    ResponseEntity<?> register(@RequestBody RegisterRequestDTO body);

}
