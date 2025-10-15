package com.library.api.service.query;

import com.library.api.dto.PageResponseDTO;
import com.library.api.dto.UserResponseDTO;
import com.library.api.exception.UserNotFoundException;
import com.library.api.mapper.UserMapper;
import com.library.api.model.User;
import com.library.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDTO findById(Long userId){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return userMapper.toResponse(user);
    }

    public UserResponseDTO findByUsername(String username){
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        return userMapper.toResponse(user);
    }

    public PageResponseDTO<UserResponseDTO> findAll(Pageable pageable){
        Page<UserResponseDTO> users = userRepository.findAll(pageable).map(userMapper::toResponse);
        return PageResponseDTO.fromPage(users);
    }
}
