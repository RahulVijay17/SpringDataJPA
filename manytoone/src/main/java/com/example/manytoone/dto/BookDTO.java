package com.example.manytoone.dto;

import lombok.Data;

@Data
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private boolean borrowed;
    private UserDTO borrowedBy;

}
