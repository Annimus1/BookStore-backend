package com.bookstore.Bookstore.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bookstore.Bookstore.domains.models.BookEntity;
import com.bookstore.Bookstore.domains.repository.IBookRepository;

@Service
public class BookService {
    IBookRepository bookRepository;

    public BookService(IBookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    // Create
    public BookEntity createBook(BookEntity book){
        return this.bookRepository.save(book);
    }

    // Delete
    public void deleteBook(BookEntity book){
        this.bookRepository.delete(book);
    }

    public void deleteBookById(long id){
        this.bookRepository.deleteById(id);
    }

    // Get
    public Optional<BookEntity> getById(long id){
        return this.bookRepository.findById(id);
    }
    
    public BookEntity getByName(String name){
        return this.bookRepository.findByName(name);
    }

    public List<BookEntity> getAllBooks(){
        return bookRepository.findAll();
    }

    public List<BookEntity> getBooksByAuthor(long id){
        System.out.println(id);
        return bookRepository.findByAuthorId(id);
    }
}
