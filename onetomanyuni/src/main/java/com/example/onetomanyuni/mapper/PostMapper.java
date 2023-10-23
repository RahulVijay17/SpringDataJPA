package com.example.onetomanyuni.mapper;

import com.example.onetomanyuni.dto.CommentDTO;
import com.example.onetomanyuni.dto.PostDTO;
import com.example.onetomanyuni.model.Comment;
import com.example.onetomanyuni.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    PostDTO toPostDTO(Post post);
    Post toPost(PostDTO postDTO);


}