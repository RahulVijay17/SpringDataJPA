package com.example.manytoone.service;

import com.example.manytoone.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);

    List<UserDTO> findAllUsers();
}
