package com.casestudy.book.service;




import com.casestudy.book.dataModel.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();
    Book getBookById(long id);
    Book saveBook(Book book);
    void deleteBookById(long id);
    Book updateBookById(long id, Book book);
}
