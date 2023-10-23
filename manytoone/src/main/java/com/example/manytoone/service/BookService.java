package com.example.manytoone.service;

import com.example.manytoone.dto.BookDTO;
import com.example.manytoone.model.Book;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BookService {

    BookDTO save(BookDTO book);
    BookDTO getBookById(Long id);
    List<BookDTO> getAllBooks();
    boolean deleteById(Long id);

    BookDTO updateBook(Long id, BookDTO updatedBook);

    BookDTO borrowBook(Long bookId, Long userId);

    BookDTO returnBook(Long bookId);

    Optional<List<BookDTO>> findBooksByTitleContaining(String keyword);

    Map<String, List<BookDTO>> findBooksByTitleContainingGrouped(String keyword);
}
