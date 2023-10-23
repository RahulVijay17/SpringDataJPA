package com.example.manytoone.mapper;

import com.example.manytoone.dto.UserDTO;
import com.example.manytoone.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(User user);
    User userDTOToUser(UserDTO userDTO);


}