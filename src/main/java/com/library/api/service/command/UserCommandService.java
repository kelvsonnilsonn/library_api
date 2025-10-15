package com.library.api.service.command;

import com.library.api.command.user.DeleteUserCommand;
import com.library.api.command.user.UpdateUsernameCommand;
import com.library.api.dto.UserResponseDTO;
import com.library.api.dto.UserUpdateDto;
import com.library.api.exception.NameAlreadyInUseUpdateException;
import com.library.api.exception.UserNotFoundException;
import com.library.api.model.User;
import com.library.api.repository.UserRepository;
import com.library.api.service.AuthenticationInformation;
import com.library.api.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserCommandService {

    private final UserRepository userRepository;
    private final AuthenticationInformation authenticationInformation;

    public void delete(DeleteUserCommand command){
        User user = userRepository.findById(command.userId()).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }

    public void update(UpdateUsernameCommand command){
        User user = authenticationInformation.getAuthenticatedUser();
        if(command.newName() != null && command.newName().equals(user.getUsername())){
            throw new NameAlreadyInUseUpdateException();
        }
        user.setUsername(command.newName());
        userRepository.save(user);
    }
}
