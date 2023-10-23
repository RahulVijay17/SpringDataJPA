package com.example.manytoone.mapper;

import com.example.manytoone.dto.BookDTO;
import com.example.manytoone.model.Book;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(target = "borrowedBy", source = "borrowedBy")
    BookDTO bookToBookDTO(Book book);

    @InheritInverseConfiguration
    Book bookDTOToBook(BookDTO bookDTO);

}