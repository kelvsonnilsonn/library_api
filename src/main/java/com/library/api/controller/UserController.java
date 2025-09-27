package com.library.api.controller;

import com.library.api.dto.UserRequestDTO;
import com.library.api.dto.UserResponseDTO;
import com.library.api.service.UserService;
import com.library.api.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// V1.1

@RestController
@RequestMapping(AppConstants.USER_BASE_PATH)
public class UserController implements UserAPI{

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping(AppConstants.CREATE_USER_PATH)
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO userRequestDTO){
        UserResponseDTO response = userService.create(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        String message = userService.delete(id);
        return ResponseEntity.ok(message);
    }

    @Override
    public ResponseEntity<UserResponseDTO> findById(Long id) {
        UserResponseDTO dto = userService.findById(id);
    return ResponseEntity.status(HttpStatus.FOUND).body(dto);
    }
}
