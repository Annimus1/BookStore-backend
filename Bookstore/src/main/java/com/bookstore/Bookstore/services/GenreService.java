package com.bookstore.Bookstore.services;

import java.util.Optional;

import com.bookstore.Bookstore.domains.models.GenreEntity;
import com.bookstore.Bookstore.domains.repository.IGenreRepository;

public class GenreService {
    IGenreRepository genreRepository;

    public GenreService(IGenreRepository genreRepository){
        this.genreRepository = genreRepository;
    }

    // Create
    public GenreEntity createGenre(GenreEntity genre){
        return this.genreRepository.save(genre);
    }

    // Delete
    public void deleteGenre(GenreEntity genre){
        this.genreRepository.delete(genre);
    }

    public void deleteGenreById(long id){
        this.genreRepository.deleteById(id);
    }

    // Get
    public Optional<GenreEntity> getById(long id){
        return this.genreRepository.findById(id);
    }
    
    public GenreEntity getByName(String name){
        return this.genreRepository.findByName(name);
    }
}
