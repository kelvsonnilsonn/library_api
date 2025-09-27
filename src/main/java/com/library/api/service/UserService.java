package com.library.api.service;

import com.library.api.dto.UserRequestDTO;
import com.library.api.dto.UserResponseDTO;
import com.library.api.mapper.UserMapper;
import com.library.api.model.User;
import com.library.api.repository.UserRepository;
import com.library.api.util.AppConstants;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// V 1.2

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void create(UserRequestDTO userRequestDTO){
        userRepository.save(UserMapper.dtoToUser(userRequestDTO));
    }

    public String delete(Long id){
        User user = findEntityById(id);
        userRepository.deleteById(id);
        return String.format(AppConstants.USER_DELETED_MSG, user.getUsername());
    }


    public UserResponseDTO findById(Long id){
        User user = findEntityById(id);
        return UserMapper.toResponse(user);
    }

    public User findEntityById(Long id){
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
