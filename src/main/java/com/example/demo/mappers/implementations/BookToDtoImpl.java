package com.example.demo.mappers.implementations;

import com.example.demo.models.Book;
import com.example.demo.mappers.interfaces.BookToDtoMapper;
import com.example.demo.models.BookRequest;
import org.springframework.stereotype.Component;

@Component
public class BookToDtoImpl implements BookToDtoMapper {

    @Override
    public Book AddBookRequestToBook(BookRequest bookRequest) {
        if (bookRequest == null)
            return null;
        return new Book(null, bookRequest.getAuthor(), bookRequest.getTitle(), bookRequest.getPrice());
    }

    @Override
    public Book EditBookRequestToBook(Long id, BookRequest bookRequest) {
        if (bookRequest == null)
            return null;
        return new Book(id, bookRequest.getAuthor(), bookRequest.getTitle(), bookRequest.getPrice());
    }
}
