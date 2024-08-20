package com.casestudy.book.service;



import com.casestudy.book.dataModel.Book;
import com.casestudy.book.exceptions.BookNotFoundException;
import com.casestudy.book.exceptions.DuplicateBookException;
import com.casestudy.book.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
//        return List.of((Book) bookRepository.findAll());
        return List.copyOf(bookRepository.findAll());
    }

    @Override
    public Book getBookById(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book of id: "+id+" NOT FOUND"));
    }

    @Override
    public Book saveBook(Book book) {
        if(bookRepository.findByTitle(book.getTitle()) != null){
            throw new DuplicateBookException("Book with title: "+book.getTitle()+" Already Exist");
        }
        return bookRepository.save(book);
    }

    @Override
    public void deleteBookById(long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book of id: "+id+" NOT FOUND"));
        bookRepository.delete(book);
    }

    @Override
    public Book updateBookById(long id, Book book) {
        Book existingBook = getBookById(id);
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setPrice(book.getPrice());
        existingBook.setStock(book.getStock());
        return bookRepository.save(existingBook);
    }
}
