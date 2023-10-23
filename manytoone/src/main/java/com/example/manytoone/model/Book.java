package com.example.manytoone.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private boolean borrowed;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User borrowedBy;
}