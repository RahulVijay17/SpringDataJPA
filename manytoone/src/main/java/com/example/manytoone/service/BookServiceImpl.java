package com.example.manytoone.service;

import com.example.manytoone.dto.BookDTO;
import com.example.manytoone.mapper.BookMapper;
import com.example.manytoone.model.Book;
import com.example.manytoone.model.User;
import com.example.manytoone.repository.BookRepository;
import com.example.manytoone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public BookDTO save(BookDTO bookDTO) {
        Book bookEntity = BookMapper.INSTANCE.bookDTOToBook(bookDTO);
        bookEntity = bookRepository.save(bookEntity);
        BookDTO savedBookDTO = BookMapper.INSTANCE.bookToBookDTO(bookEntity);
        return savedBookDTO;
    }

    @Override
    public BookDTO getBookById(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        return bookOptional.map(BookMapper.INSTANCE::bookToBookDTO).orElse(null);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(BookMapper.INSTANCE::bookToBookDTO).collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO updatedBook) {
        // Retrieve the existing book by its ID
        Optional<Book> existingBookOptional = bookRepository.findById(id);

        if (existingBookOptional.isPresent()) {
            // Get the existing book
            Book existingBook = existingBookOptional.get();

            // Update the properties of the existing book with values from the updatedBook
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setBorrowed(updatedBook.isBorrowed());

            // Save the updated book to the database
            existingBook = bookRepository.save(existingBook);

            // Map the updated book entity back to a DTO
            return BookMapper.INSTANCE.bookToBookDTO(existingBook);
        } else {
            // Book with the given ID was not found
            return null; // You can handle this case appropriately, e.g., return an error DTO
        }
    }

    @Override
    public BookDTO borrowBook(Long bookId, Long userId) {
        // Checking if the book with the given ID exists
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (!bookOptional.isPresent()) {
            // Handling the case where the book is not found
            return null;
        }

        Book book = bookOptional.get();

        // Checking if the book is already borrowed
        if (book.isBorrowed()) {
            // Handling the case where the book is already borrowed
            return null;
        }

        // Checking if the user with the given ID exists
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            // Handling case where the user is not found
            return null;
        }

        // If all checks pass, borrow the book
        book.setBorrowedBy(user);
        book.setBorrowed(true);
        Book savedBook = bookRepository.save(book);

        // Map the saved book entity to a DTO and return it
        return BookMapper.INSTANCE.bookToBookDTO(savedBook);
    }
    @Override
    public BookDTO returnBook(Long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();

            if (book.isBorrowed()) {
                book.setBorrowedBy(null);
                book.setBorrowed(false);
                book = bookRepository.save(book);

                // Using mapper to map the Book entity to a BookDTO
                BookDTO bookDTO = BookMapper.INSTANCE.bookToBookDTO(book);

                return bookDTO;
            }
        }
        // Handling errors (e.g., book not found, book not borrowed)
        return null;
    }

    @Override
    public Optional<List<BookDTO>> findBooksByTitleContaining(String keyword) {
        Optional<List<Book>> matchingBooks = bookRepository.findByTitleContaining(keyword);

        if (matchingBooks.isPresent()) {
            List<BookDTO> bookDTOs = matchingBooks.get()
                    .stream()
                    .map(BookMapper.INSTANCE::bookToBookDTO)
                    .collect(Collectors.toList());
            return Optional.of(bookDTOs);
        } else {
            return Optional.empty(); // No books found with the specified keyword
        }
    }

    @Override
    public Map<String, List<BookDTO>> findBooksByTitleContainingGrouped(String keyword) {
        Optional<List<Book>> matchingBooks = bookRepository.findByTitleContaining(keyword);

        if (matchingBooks.isPresent()) {
            List<BookDTO> bookDTOs = matchingBooks.get()
                    .stream()
                    .map(BookMapper.INSTANCE::bookToBookDTO)
                    .collect(Collectors.toList());

            // Group the books by their title
            Map<String, List<BookDTO>> groupedBooks = bookDTOs.stream()
                    .collect(Collectors.groupingBy(BookDTO::getTitle));

            return groupedBooks;
        } else {
            // No books found with the specified keyword, return an empty map
            return Collections.emptyMap();
        }
    }


}
