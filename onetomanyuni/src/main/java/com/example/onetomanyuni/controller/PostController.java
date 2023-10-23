package com.example.onetomanyuni.controller;

import com.example.onetomanyuni.dto.PostDTO;
import com.example.onetomanyuni.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping
    //http://localhost:8080/api/posts
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        PostDTO createdPostDTO = postService.createPost(postDTO);
        return new ResponseEntity<>(createdPostDTO, HttpStatus.CREATED);
    }

    @GetMapping
    //http://localhost:8080/api/posts
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> postDTOs = postService.getAllPosts();
        return ResponseEntity.ok(postDTOs);
    }

    @GetMapping("/{id}")
    //http://localhost:8080/api/posts/1
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        PostDTO postDTO = postService.getPostById(id);
        if (postDTO != null) {
            return ResponseEntity.ok(postDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    //http://localhost:8080/api/posts/1
    public ResponseEntity<PostDTO> updatePostWithComments(@PathVariable Long id, @RequestBody PostDTO updatedPostDTO) {
        PostDTO updatedPost = postService.updatePostWithComments(id, updatedPostDTO);
        if (updatedPost != null) {
            return ResponseEntity.ok(updatedPost);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("updatepost/{id}")
    //http://localhost:8080/api/posts/updatepost/1
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestBody PostDTO updatedPostDTO) {
        PostDTO updatedPost = postService.updatePost(id, updatedPostDTO);
        if (updatedPost != null) {
            return ResponseEntity.ok(updatedPost);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    //http://localhost:8080/api/posts/2
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}


//POST JSON
/*
{
        "title": "Sample Post ",
        "description": "This is a sample post.",
        "comments": [
        {
        "text": "First comment"
        },
        {
        "text": "Second comment"
        }
        ]
        }*/

//PUT JSON updatePostWithComments

/*
{
        "id": 1,                     // The ID of the post you want to update
        "title": "Updated Title",     // The new title for the post
        "description": "Updated description", // The new description for the post
        "comments": [
        {
        "id": 1,                  // The ID of the existing comment
        "text": "Updated Comment"  // The new text for the comment
        },
        {
        "id": 2,                  // The ID of another existing comment
        "text": "Another Updated Comment"  // The new text for the comment
        }
        ]
        }
*/

//PUT updatePost
/*{
        "id": 1,                     // The ID of the post you want to update
        "title": "Rahul Titles",     // The new title for the post
        "description": "Updated description" // The new description for the post
        }*/
