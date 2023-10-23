package com.example.manytoone.service;

import com.example.manytoone.dto.UserDTO;
import com.example.manytoone.mapper.UserMapper;
import com.example.manytoone.model.User;
import com.example.manytoone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    public UserDTO createUser(UserDTO userDTO) {
        User user = UserMapper.INSTANCE.userDTOToUser(userDTO);
        User savedUser = userRepository.save(user);
        return UserMapper.INSTANCE.userToUserDTO(savedUser);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper.INSTANCE::userToUserDTO).collect(Collectors.toList());
    }
}
