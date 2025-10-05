package com.library.api.controller;

import com.library.api.dto.auth.AuthResponseDTO;
import com.library.api.dto.auth.LoginRequestDTO;
import com.library.api.dto.auth.RegisterRequestDTO;
import com.library.api.service.SecurityService;
import com.library.api.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppConstants.AUTH_BASE_PATH)
@RequiredArgsConstructor
public class AuthApiController implements AuthAPI {

    private final SecurityService securityService;

    @PostMapping(AppConstants.LOGIN_PATH)
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO dto){
        AuthResponseDTO response = securityService.login(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping(AppConstants.REGISTER_PATH)
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO dto){
        AuthResponseDTO response = securityService.register(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping(AppConstants.CHANGE_PASSWORD_PATH)
    public ResponseEntity<String> updatePassword(@RequestParam String password){
        String message = securityService.update(password);
        return ResponseEntity.ok(message);
    }
}
