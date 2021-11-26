package com.example.demo.controllers;

import com.example.demo.mappers.interfaces.BookToDtoMapper;
import com.example.demo.models.Book;
import com.example.demo.models.BookRequest;
import com.example.demo.services.BookService;
import com.example.demo.services.DefaultBookService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final BookToDtoMapper mapper;

    public BookController(DefaultBookService bookService, @Qualifier("bookToDtoImpl") BookToDtoMapper mapper) {
        this.bookService = bookService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) throws Exception {
        return bookService.getBookById(id);
    }

    @GetMapping
    public List<Book> getAllBooks(@RequestParam(required = false) String author) {
        if (author != null)
            return bookService.findByAuthor(author);

        return bookService.getAllBooks();
    }

    @PostMapping
    public void addBook(@RequestBody BookRequest request) {
        bookService.addBook(mapper.AddBookRequestToBook(request));
    }

    @PutMapping("/{id}")
    public void editBook(@PathVariable Long id, @RequestBody BookRequest request) {
        bookService.editBook(mapper.EditBookRequestToBook(id, request));
    }
}