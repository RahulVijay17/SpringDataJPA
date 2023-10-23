package com.example.manytoone.controller;

import com.example.manytoone.dto.UserDTO;
import com.example.manytoone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    //http://localhost:8080/api/users/create
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO savedUser = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping
    //http://localhost:8080/api/users
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.findAllUsers();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    //POST User
    /*{
        "name": "Jeeva Doe"
    }*/


}
