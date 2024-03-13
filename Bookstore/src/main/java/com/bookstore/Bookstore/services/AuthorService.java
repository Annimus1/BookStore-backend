package com.bookstore.Bookstore.services;

import java.util.Optional;

import com.bookstore.Bookstore.domains.models.AuthorEntity;
import com.bookstore.Bookstore.domains.repository.IAuthorRepository;

public class AuthorService {
    IAuthorRepository authorRepository;

    public AuthorService(IAuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    // Create
    public AuthorEntity createAuthor(AuthorEntity author){
        return this.authorRepository.save(author);
    }

    // Delete
    public void deleteAuthor(AuthorEntity author){
        this.authorRepository.delete(author);
    }

    public void deleteAuthorById(long id){
        this.authorRepository.deleteById(id);
    }

    // Get
    public Optional<AuthorEntity> getById(long id){
        return this.authorRepository.findById(id);
    }
    
    public AuthorEntity getByName(String name){
        return this.authorRepository.findByName(name);
    }
}
