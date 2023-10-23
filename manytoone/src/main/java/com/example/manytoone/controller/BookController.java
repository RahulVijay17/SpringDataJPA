package com.example.manytoone.controller;

import com.example.manytoone.dto.BookDTO;
import com.example.manytoone.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    //http://localhost:8080/api
    public ResponseEntity<BookDTO> saveBook(@RequestBody BookDTO bookDTO) {
        BookDTO savedBook = bookService.save(bookDTO);

        if (savedBook != null) {
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    //http://localhost:8080/api/1
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO book = bookService.getBookById(id);
        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    //http://localhost:8080/api
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> allBooks = bookService.getAllBooks();
        return ResponseEntity.ok(allBooks);
    }

    @DeleteMapping("/{id}")
    //http://localhost:8080/api/1
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean deleted = bookService.deleteById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    //http://localhost:8080/api/3
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO updatedBook) {
        BookDTO bookDTO = bookService.updateBook(id, updatedBook);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @PostMapping("/{bookId}/borrow/{userId}")
    //http://localhost:8080/api/4/borrow/1
    public ResponseEntity<BookDTO> borrowBook(@PathVariable Long bookId, @PathVariable Long userId) {
        BookDTO borrowedBook = bookService.borrowBook(bookId, userId);
        if (borrowedBook != null) {
            return ResponseEntity.ok(borrowedBook);
        } else {
            return ResponseEntity.badRequest().build(); // or a more descriptive error response
        }
    }

    @PostMapping("/{bookId}/return")
    //http://localhost:8080/api/1/return
    public ResponseEntity<BookDTO> returnBook(@PathVariable Long bookId) {
        BookDTO returnedBook = bookService.returnBook(bookId);

        if (returnedBook != null) {
            return ResponseEntity.ok(returnedBook);
        } else {
            return ResponseEntity.badRequest().build(); // or a more descriptive error response
        }
    }

    @GetMapping("/search")
    //http://localhost:8080/api/search?keyword=Core
    public ResponseEntity<?> searchBooksByTitleName(@RequestParam String title) {
        Optional<List<BookDTO>> booksByTitleContaining = bookService.findBooksByTitleContaining(title);
        if (booksByTitleContaining.isPresent()) {
            List<BookDTO> matchingBooks = booksByTitleContaining.get();
            return ResponseEntity.ok(matchingBooks);
        } else {
            return ResponseEntity.notFound().build(); // No books found with the specified keyword
        }
    }

    @GetMapping("/searchgrouped")
    //http://localhost:8080/api/searchgrouped?keyword=Core Java
    public ResponseEntity<?> searchBooksGroupedByTitle(@RequestParam String keyword) {
        Map<String, List<BookDTO>> groupedBooks = bookService.findBooksByTitleContainingGrouped(keyword);
        if (!groupedBooks.isEmpty()) {
            return ResponseEntity.ok(groupedBooks);
        } else {
            return ResponseEntity.notFound().build(); // No books found with the specified keyword
        }
    }


    //POST JSON
   /* {
        "title": "Spring Boot",
            "author": "Ramesh Fadatare",
            "borrowed": false
    }*/

    //PUT JSON
   /* {
        "title": "Core Java",
            "author": "Rahul Vijay",
            "borrowed": true
    }*/

    //reference
    //https://www.javaguides.net/2023/08/library-management-system-project-using-spring-boot.html
}
