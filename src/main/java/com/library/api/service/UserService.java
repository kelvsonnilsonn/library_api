package com.library.api.service;

import com.library.api.dto.PageResponseDTO;
import com.library.api.dto.UserRequestDTO;
import com.library.api.dto.UserResponseDTO;
import com.library.api.mapper.UserMapper;
import com.library.api.model.User;
import com.library.api.repository.UserRepository;
import com.library.api.util.AppConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// V 1.3

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserResponseDTO create(UserRequestDTO userRequestDTO){
        User savedUser = userRepository.save(userMapper.dtoToUser(userRequestDTO));
        return userMapper.toResponse(savedUser);
    }

    @Transactional
    public String delete(Long id){
        User user = findEntityById(id);
        userRepository.deleteById(id);
        return String.format(AppConstants.USER_DELETED_MSG, user.getUsername());
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findById(Long id){
        User user = findEntityById(id);
        return userMapper.toResponse(user);
    }

    @Transactional(readOnly = true)
    public PageResponseDTO<UserResponseDTO> findAll(Pageable pageable){
        Page<UserResponseDTO> users = userRepository.findAll(pageable).map(userMapper::toResponse);
        return PageResponseDTO.fromPage(users);
    }

    @Transactional(readOnly = true)
    public User findEntityById(Long id){
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
