package com.example.manytoone.repository;

import com.example.manytoone.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<List<Book>> findByTitleContaining(String keyword);
}
