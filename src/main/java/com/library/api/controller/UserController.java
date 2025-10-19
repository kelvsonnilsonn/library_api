package com.library.api.controller;

import com.library.api.command.user.UpdateUsernameCommand;
import com.library.api.service.command.UserCommandService;
import com.library.api.service.query.UserQueryService;
import com.library.api.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// V1.5

@RestController
@RequestMapping(AppConstants.USER_BASE_PATH)
@RequiredArgsConstructor
@PreAuthorize(AppConstants.PRE_AUTHORIZE_ALL_REQUISITION)
public class UserController implements UserAPI{

    private final UserCommandService commandService;
    private final UserQueryService queryService;

    @GetMapping
    public ResponseEntity<?> findUser(Pageable pageable, @RequestParam(required=false) Long id, @RequestParam(required = false) String name){
        if(id != null){
            return ResponseEntity.ok(queryService.findById(id));
        }
        if(name != null){
            return ResponseEntity.ok(queryService.findByUsername(name));
        }
        return ResponseEntity.ok(queryService.findAll(pageable));
    }

    @PutMapping
    public ResponseEntity<Void> updateName(@RequestBody UpdateUsernameCommand command){
        commandService.update(command);
        return ResponseEntity.ok().build();
    }
}
