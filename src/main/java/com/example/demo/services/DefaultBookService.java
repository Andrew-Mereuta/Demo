package com.example.demo.services;

import com.example.demo.mappers.interfaces.BookToEntityMapper;
import com.example.demo.models.Book;
import com.example.demo.models.BookEntity;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultBookService implements BookService {

    private final BookRepository bookRepository;
    private final BookToEntityMapper mapper;

    public DefaultBookService(BookRepository bookRepository, @Qualifier("bookToEntityImpl") BookToEntityMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    @Override
    public Book getBookById(Long id) throws Exception {
        BookEntity bookEntity = bookRepository
                .findById(id)
                .orElseThrow(() -> new Exception("Book not found: id = " + id));

        return mapper.bookEntityToBook(bookEntity);
    }

    @Override
    public List<Book> getAllBooks() {
        Iterable<BookEntity> iterable = bookRepository.findAll();

        ArrayList<Book> books = new ArrayList<>();
        for (BookEntity bookEntity : iterable) {
            books.add(mapper.bookEntityToBook(bookEntity));
        }

        return books;
    }

    @Override
    public void addBook(Book book) {
        BookEntity bookEntity = mapper.bookToBookEntity(book);
        bookRepository.save(bookEntity);
    }

    @Override
    public void editBook(Book book) {
        bookRepository.save(mapper.bookToBookEntity(book));
    }

    @Override
    public List<Book> findByAuthor(String author) {
        List<BookEntity> allByAuthorContaining = bookRepository.findAllByAuthorContaining(author);
        List<Book> books = new ArrayList<>();
        for (BookEntity bookEntity : allByAuthorContaining) {
            books.add(mapper.bookEntityToBook(bookEntity));
        }
        return books;
    }
}