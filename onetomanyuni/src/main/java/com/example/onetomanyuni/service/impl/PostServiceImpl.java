package com.example.onetomanyuni.service.impl;

import com.example.onetomanyuni.dto.CommentDTO;
import com.example.onetomanyuni.dto.PostDTO;
import com.example.onetomanyuni.mapper.PostMapper;
import com.example.onetomanyuni.model.Comment;
import com.example.onetomanyuni.model.Post;
import com.example.onetomanyuni.repository.PostRepository;
import com.example.onetomanyuni.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Override
    public PostDTO createPost(PostDTO postDTO) {
        // Map PostDTO to Post entity using your mapper
        Post postEntity = PostMapper.INSTANCE.toPost(postDTO);
        // Save the Post entity to the database
        Post createdPost = postRepository.save(postEntity);
        // Map the created Post back to a PostDTO
        return PostMapper.INSTANCE.toPostDTO(createdPost);
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(PostMapper.INSTANCE::toPostDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO getPostById(Long id) {
        Optional<Post> postOptional = postRepository.findById(id);

        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            return PostMapper.INSTANCE.toPostDTO(post);
        } else {
            return null;
        }
    }

    @Override
    public PostDTO updatePostWithComments(Long postId, PostDTO updatedPostDTO) {
        // Check if the post exists
        Optional<Post> existingPostOptional = postRepository.findById(postId);

        if (existingPostOptional.isPresent()) {
            // Get the existing post
            Post existingPost = existingPostOptional.get();

            // Update the existing post with the new data from the updatedPostDTO
            existingPost.setTitle(updatedPostDTO.getTitle());
            existingPost.setDescription(updatedPostDTO.getDescription());

            // Handle comments updates
            List<CommentDTO> updatedComments = updatedPostDTO.getComments();
            List<Comment> existingComments = existingPost.getComments();

            // Iterate through updated comments and update existing ones or create new ones
            updatedComments.forEach(updatedComment -> {
                // Check if the comment exists in the existing comments list
                Comment existingComment = existingComments.stream()
                        .filter(c -> c.getId().equals(updatedComment.getId()))
                        .findFirst()
                        .orElse(null);

                if (existingComment != null) {
                    // Update the existing comment
                    existingComment.setText(updatedComment.getText());
                } else {
                    // Create a new comment and add it to the existing comments
                    Comment newComment = new Comment();
                    newComment.setText(updatedComment.getText());
                    existingComments.add(newComment);
                }
            });


            // Remove comments that are not present in the updatedPostDTO
            existingComments.removeIf(existingComment -> updatedComments.stream()
                    .noneMatch(updatedComment -> updatedComment.getId().equals(existingComment.getId())));

            // Save the updated post
            Post updatedPost = postRepository.save(existingPost);

            // Map the updated post to a PostDTO and return it
            return PostMapper.INSTANCE.toPostDTO(updatedPost);
        } else {
            // Handle the case where the post with the specified ID is not found
            // You can return null or throw a custom exception
            return null;
        }
    }

    @Override
    public PostDTO updatePost(Long postId, PostDTO updatedPostDTO) {
        // Check if the post exists
        Optional<Post> existingPostOptional = postRepository.findById(postId);

        if (existingPostOptional.isPresent()) {
            // Get the existing post
            Post existingPost = existingPostOptional.get();

            // Update the existing post with the new data from the updatedPostDTO
            existingPost.setTitle(updatedPostDTO.getTitle());
            existingPost.setDescription(updatedPostDTO.getDescription());

            // Save the updated post (without affecting the comments)
            Post updatedPost = postRepository.save(existingPost);

            // Map the updated post to a PostDTO and return it
            return PostMapper.INSTANCE.toPostDTO(updatedPost);
        } else {
            // Handle the case where the post with the specified ID is not found
            // You can return null or throw a custom exception
            return null;
        }
    }

    @Override
    public void deletePost(Long postId) {
        // Check if the post exists
        if (postRepository.existsById(postId)) {
            // Delete the post by ID
            postRepository.deleteById(postId);
        } else {
            // Handle the case where the post with the specified ID is not found
            // You can return null or throw a custom exception
            throw new RuntimeException("Post not found with ID: " + postId);
        }
    }
}
