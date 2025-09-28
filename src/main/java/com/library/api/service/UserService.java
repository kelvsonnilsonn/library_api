package com.library.api.service;

import com.library.api.dto.UserRequestDTO;
import com.library.api.dto.UserResponseDTO;
import com.library.api.mapper.UserMapper;
import com.library.api.model.User;
import com.library.api.repository.UserRepository;
import com.library.api.util.AppConstants;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

// V 1.3

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponseDTO create(UserRequestDTO userRequestDTO){
        User savedUser = userRepository.save(userMapper.dtoToUser(userRequestDTO));
        return userMapper.toResponse(savedUser);
    }

    public String delete(Long id){
        User user = findEntityById(id);
        userRepository.deleteById(id);
        return String.format(AppConstants.USER_DELETED_MSG, user.getUsername());
    }


    public UserResponseDTO findById(Long id){
        User user = findEntityById(id);
        return userMapper.toResponse(user);
    }

    public User findEntityById(Long id){
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
