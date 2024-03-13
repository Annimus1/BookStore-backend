package com.bookstore.Bookstore.domains.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.Bookstore.domains.models.BookEntity;

public interface IBookRepository extends JpaRepository<BookEntity, Long>{
    public BookEntity findByName(String name);
}
