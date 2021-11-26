package com.example.demo.services;

import com.example.demo.models.Book;

import java.util.List;

public interface BookService {
    Book getBookById(Long id) throws Exception;

    List<Book> getAllBooks();

    void addBook(Book book);

    void editBook(Book book);

    List<Book> findByAuthor(String author);
}
