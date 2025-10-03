package com.library.api.service;

import com.library.api.dto.auth.AuthResponseDTO;
import com.library.api.dto.auth.LoginRequestDTO;
import com.library.api.dto.auth.RegisterRequestDTO;
import com.library.api.exception.FailedLoginAttemptException;
import com.library.api.exception.PasswordAlreadyInUseUpdateException;
import com.library.api.exception.UserAlreadyExistsException;
import com.library.api.exception.UserNotFoundException;
import com.library.api.model.Password;
import com.library.api.model.User;
import com.library.api.repository.UserRepository;
import com.library.api.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationInformation authenticationInformation;

    @Transactional(readOnly = true)
    public AuthResponseDTO login(@RequestBody LoginRequestDTO body){
        User user = userRepository.findByUsername(body.username()).orElseThrow(UserNotFoundException::new);
        if(!(passwordEncoder.matches(body.password(), user.getPassword()))) {
            throw new FailedLoginAttemptException();
        }
        String token = tokenService.generateToken(user);
        return new AuthResponseDTO(token, user.getUsername());
    }

    @Transactional
    public AuthResponseDTO register(@RequestBody RegisterRequestDTO body){
        Optional<User> user = userRepository.findByUsername(body.username());
        if(user.isPresent()){
            throw new UserAlreadyExistsException();
        }
        Password password = new Password(passwordEncoder.encode(body.password()));
        User newUser = new User(body.username(), password);
        userRepository.save(newUser);
        String token = tokenService.generateToken(newUser);
        return new AuthResponseDTO(token, newUser.getUsername());
    }

    @Transactional
    public String update(String newPassword){
        User user = authenticationInformation.getAuthenticatedUser();
        if(passwordEncoder.matches(newPassword, user.getPassword())){
            throw new PasswordAlreadyInUseUpdateException();
        }
        Password encodedPassword = Password.of(newPassword, passwordEncoder);
        user.changePassword(encodedPassword);
        userRepository.save(user);
        return "Senha alterada com sucesso.";
    }

}
