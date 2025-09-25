package com.library.api.controller;

import com.library.api.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserAPI {
    ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO);
    ResponseEntity<String> delete(@RequestParam Long id);
    ResponseEntity<UserDTO> findById(@RequestParam Long id);
}
