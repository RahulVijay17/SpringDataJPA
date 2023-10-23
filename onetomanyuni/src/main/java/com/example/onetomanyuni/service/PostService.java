package com.example.onetomanyuni.service;

import com.example.onetomanyuni.dto.PostDTO;

import java.util.List;

public interface PostService {

    PostDTO createPost(PostDTO postDTO);

    List<PostDTO> getAllPosts();

    PostDTO getPostById(Long id);

    PostDTO updatePost(Long postId, PostDTO updatedPostDTO);

    PostDTO updatePostWithComments(Long postId, PostDTO updatedPostDTO);
    void deletePost(Long postId);

}
