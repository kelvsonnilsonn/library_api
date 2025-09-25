package com.library.api.service;

import com.library.api.dto.UserDTO;
import com.library.api.model.User;
import com.library.api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userDAO;

    @Autowired
    public UserService(UserRepository userDAO){
        this.userDAO = userDAO;
    }

    public void create(UserDTO userDTO){
        User user = new User(userDTO.username(), userDTO.password());
        userDAO.save(user);
    }

    public User findById(Long id){
        return userDAO.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
