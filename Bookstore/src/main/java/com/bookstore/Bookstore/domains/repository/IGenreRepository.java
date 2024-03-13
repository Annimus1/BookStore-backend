package com.bookstore.Bookstore.domains.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.Bookstore.domains.models.GenreEntity;

public interface IGenreRepository extends JpaRepository<GenreEntity,Long>{
    public GenreEntity findByName(String name);
}
