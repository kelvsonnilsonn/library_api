package com.library.api.controller.api;

import com.library.api.dto.UserRequestDTO;
import com.library.api.dto.UserResponseDTO;
import com.library.api.service.UserService;
import com.library.api.util.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// V1.1

@RestController
@RequestMapping(AppConstants.USER_BASE_PATH)
public class UserApiController implements UserAPI{

    private final UserService userService;

    public UserApiController(UserService userService){
        this.userService = userService;
    }

    @PostMapping(AppConstants.CREATE_PATH)
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO userRequestDTO){
        UserResponseDTO response = userService.create(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping(AppConstants.DELETE_PATH)
    public ResponseEntity<String> delete(Long id) {
        String message = userService.delete(id);
        return ResponseEntity.ok(message);
    }

    @GetMapping(AppConstants.SEARCH_PATH)
    public ResponseEntity<UserResponseDTO> findById(Long id) {
        UserResponseDTO dto = userService.findById(id);
    return ResponseEntity.status(HttpStatus.FOUND).body(dto);
    }
}
