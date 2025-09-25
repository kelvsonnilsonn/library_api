package com.library.api.controller;

import com.library.api.dto.UserDTO;
import com.library.api.service.UserService;
import com.library.api.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// V1.0

@RestController
@RequestMapping(AppConstants.USER_BASE_PATH)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping(AppConstants.CREATE_USER_PATH)
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO){
        userService.create(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }
}
