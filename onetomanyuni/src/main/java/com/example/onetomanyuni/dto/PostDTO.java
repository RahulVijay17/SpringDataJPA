package com.example.onetomanyuni.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDTO {
    private Long id;
    private String title;
    private String description;
    private List<CommentDTO> comments;
}
