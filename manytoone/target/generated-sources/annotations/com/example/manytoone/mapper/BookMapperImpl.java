package com.example.manytoone.mapper;

import com.example.manytoone.dto.BookDTO;
import com.example.manytoone.dto.UserDTO;
import com.example.manytoone.model.Book;
import com.example.manytoone.model.User;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-23T16:57:39+0530",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.2 (Oracle Corporation)"
)
public class BookMapperImpl implements BookMapper {

    @Override
    public BookDTO bookToBookDTO(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDTO bookDTO = new BookDTO();

        bookDTO.setBorrowedBy( userToUserDTO( book.getBorrowedBy() ) );
        bookDTO.setId( book.getId() );
        bookDTO.setTitle( book.getTitle() );
        bookDTO.setAuthor( book.getAuthor() );
        bookDTO.setBorrowed( book.isBorrowed() );

        return bookDTO;
    }

    @Override
    public Book bookDTOToBook(BookDTO bookDTO) {
        if ( bookDTO == null ) {
            return null;
        }

        Book book = new Book();

        book.setBorrowedBy( userDTOToUser( bookDTO.getBorrowedBy() ) );
        book.setId( bookDTO.getId() );
        book.setTitle( bookDTO.getTitle() );
        book.setAuthor( bookDTO.getAuthor() );
        book.setBorrowed( bookDTO.isBorrowed() );

        return book;
    }

    protected UserDTO userToUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( user.getId() );
        userDTO.setName( user.getName() );

        return userDTO;
    }

    protected User userDTOToUser(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDTO.getId() );
        user.setName( userDTO.getName() );

        return user;
    }
}
