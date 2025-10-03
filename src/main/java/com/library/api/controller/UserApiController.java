package com.library.api.controller;

import com.library.api.dto.PageResponseDTO;
import com.library.api.dto.UserResponseDTO;
import com.library.api.dto.UserUpdateDto;
import com.library.api.service.UserService;
import com.library.api.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// V1.4

@RestController
@RequestMapping(AppConstants.USER_BASE_PATH)
@RequiredArgsConstructor
public class UserApiController implements UserAPI{

    private final UserService userService;

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

    @GetMapping(AppConstants.SEARCH_NAME_PATH)
    public ResponseEntity<UserResponseDTO> findByUsername(@RequestParam String name){
        UserResponseDTO dto = userService.findByUsername(name);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO<UserResponseDTO>> findAll(Pageable pageable){
        PageResponseDTO<UserResponseDTO> users = userService.findAll(pageable);
        return ResponseEntity.ok(users);
    }

    @PutMapping
    public ResponseEntity<UserResponseDTO> updateName(@RequestBody UserUpdateDto updateDto){
        UserResponseDTO user = userService.update(updateDto);
        return ResponseEntity.ok(user);
    }
}
