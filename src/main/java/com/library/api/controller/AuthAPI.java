package com.library.api.controller;

import com.library.api.dto.auth.LoginRequestDTO;
import com.library.api.dto.auth.RegisterRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Segurança", description = "Sistema de autenticação")
public interface AuthAPI {

    @Operation(summary = "Logar um usuário", description = "Loga de um usuário")
    ResponseEntity<?> login(@RequestBody LoginRequestDTO body);

    @Operation(summary = "Registrar um usuário", description = "Registra um usuário")
    ResponseEntity<?> register(@RequestBody RegisterRequestDTO body);

}
