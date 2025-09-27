package com.library.api.controller;

import com.library.api.dto.UserRequestDTO;
import com.library.api.dto.UserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserAPI {
    ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO userRequestDTO);
    ResponseEntity<String> delete(@RequestParam Long id);
    ResponseEntity<UserResponseDTO> findById(@RequestParam Long id);
}
