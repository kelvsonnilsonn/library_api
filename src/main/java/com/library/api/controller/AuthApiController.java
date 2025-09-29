package com.library.api.controller;

import com.library.api.dto.auth.LoginRequestDTO;
import com.library.api.dto.auth.AuthResponseDTO;
import com.library.api.dto.auth.RegisterRequestDTO;
import com.library.api.model.Password;
import com.library.api.model.User;
import com.library.api.repository.UserRepository;
import com.library.api.security.TokenService;
import com.library.api.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(AppConstants.AUTH_BASE_PATH)
@RequiredArgsConstructor
public class AuthApiController implements AuthAPI {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping(AppConstants.LOGIN_PATH)
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO body){
        User user = this.userRepository.findByUsername(body.username()).orElseThrow(() -> new RuntimeException("User não encontrado"));
        if(passwordEncoder.matches(body.password().getPassword(), user.getPassword())){
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new AuthResponseDTO(token, user.getUsername()));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(AppConstants.REGISTER_PATH)
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO body){
        Optional<User> user = this.userRepository.findByUsername(body.username());
        if(user.isEmpty()){
            Password password = new Password(passwordEncoder.encode(body.password().getPassword()));
            User newUser = new User(body.username(), password);
            this.userRepository.save(newUser);
            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new AuthResponseDTO(token, newUser.getUsername()));
        }
        return ResponseEntity.badRequest().build();
    }
}
