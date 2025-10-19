package com.library.api.controller;

import com.library.api.command.user.UpdateUsernameCommand;
import com.library.api.service.command.UserCommandService;
import com.library.api.service.query.UserQueryService;
import com.library.api.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppConstants.USER_BASE_PATH)
@RequiredArgsConstructor
@PreAuthorize(AppConstants.PRE_AUTHORIZE_ALL_REQUISITION)
public class UserController implements UserAPI{

    private final UserCommandService commandService;
    private final UserQueryService queryService;

    @PutMapping
    public ResponseEntity<Void> updateName(@RequestBody UpdateUsernameCommand command){
        commandService.update(command);
        return ResponseEntity.ok().build();
    }
}
