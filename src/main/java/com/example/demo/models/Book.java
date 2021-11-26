package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Book {
    Long id;
    String author;
    String title;
    Double price;
}
