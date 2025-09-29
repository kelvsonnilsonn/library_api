package com.library.api.controller;

import com.library.api.dto.PageResponseDTO;
import com.library.api.dto.UserRequestDTO;
import com.library.api.dto.UserResponseDTO;
import com.library.api.service.UserService;
import com.library.api.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// V1.3

@RestController
@RequestMapping(AppConstants.USER_BASE_PATH)
@RequiredArgsConstructor
public class UserApiController implements UserAPI{

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO userRequestDTO){
        UserResponseDTO response = userService.create(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping(AppConstants.ID_PATH)
    public ResponseEntity<String> delete(@PathVariable Long id) {
        String message = userService.delete(id);
        return ResponseEntity.ok(message);
    }

    @GetMapping(AppConstants.ID_PATH)
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        UserResponseDTO dto = userService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO<UserResponseDTO>> findAll(Pageable pageable){
        PageResponseDTO<UserResponseDTO> users = userService.findAll(pageable);
        return ResponseEntity.ok(users);
    }
}
