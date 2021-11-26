package com.example.demo.mappers.interfaces;

import com.example.demo.models.Book;
import com.example.demo.models.BookRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookToDtoMapper {
    Book AddBookRequestToBook(BookRequest bookRequest);

    Book EditBookRequestToBook(Long id, BookRequest bookRequest);
}