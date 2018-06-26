package com.spring.springt3.services;

import com.spring.springt3.models.Book;

import java.util.List;

public interface BookService {
    List<Book> getAll();
    Book findOne(Integer id);
    boolean update(Book book);
    boolean delete(Integer id  );
    boolean create(Book book);
}
