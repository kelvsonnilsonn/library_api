package com.library.api.controller;

import com.library.api.command.user.DeleteUserCommand;
import com.library.api.command.user.UpdateUsernameCommand;
import com.library.api.dto.PageResponseDTO;
import com.library.api.dto.UserResponseDTO;
import com.library.api.service.command.UserCommandService;
import com.library.api.service.query.UserQueryService;
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

    private final UserCommandService commandService;
    private final UserQueryService queryService;

    @DeleteMapping(AppConstants.ID_PATH)
    public ResponseEntity<Void> delete(@RequestBody DeleteUserCommand command) {
        commandService.delete(command);
        return ResponseEntity.ok().build();
    }

    @GetMapping(AppConstants.ID_PATH)
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        UserResponseDTO dto = queryService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(AppConstants.SEARCH_NAME_PATH)
    public ResponseEntity<UserResponseDTO> findByUsername(@RequestParam String name){
        UserResponseDTO dto = queryService.findByUsername(name);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO<UserResponseDTO>> findAll(Pageable pageable){
        PageResponseDTO<UserResponseDTO> users = queryService.findAll(pageable);
        return ResponseEntity.ok(users);
    }

    @PutMapping
    public ResponseEntity<Void> updateName(@RequestBody UpdateUsernameCommand command){
        commandService.update(command);
        return ResponseEntity.ok().build();
    }
}
