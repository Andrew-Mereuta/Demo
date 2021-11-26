package com.example.demo.mappers.interfaces;

import com.example.demo.models.Book;
import com.example.demo.models.BookEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookToEntityMapper {
    BookEntity bookToBookEntity(Book book);

    Book bookEntityToBook(BookEntity bookEntity);
}