package com.example.demo.mappers.implementations;

import com.example.demo.models.Book;
import com.example.demo.models.BookEntity;
import com.example.demo.mappers.interfaces.BookToEntityMapper;
import org.springframework.stereotype.Component;

@Component
public class BookToEntityImpl implements BookToEntityMapper {

    @Override
    public BookEntity bookToBookEntity(Book book) {
        if (book == null) {
            return null;
        }

        BookEntity bookEntity = new BookEntity();

        bookEntity.setId(book.getId());
        bookEntity.setAuthor(book.getAuthor());
        bookEntity.setTitle(book.getTitle());
        bookEntity.setPrice(book.getPrice());

        return bookEntity;
    }

    @Override
    public Book bookEntityToBook(BookEntity bookEntity) {
        if (bookEntity == null) {
            return null;
        }

        Long id = null;
        String author = null;
        String title = null;
        Double price = null;

        id = bookEntity.getId();
        author = bookEntity.getAuthor();
        title = bookEntity.getTitle();
        price = bookEntity.getPrice();

        return new Book(id, author, title, price);
    }
}