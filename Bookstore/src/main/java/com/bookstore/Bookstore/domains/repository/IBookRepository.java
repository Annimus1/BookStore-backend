package com.bookstore.Bookstore.domains.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookstore.Bookstore.domains.models.BookEntity;

public interface IBookRepository extends JpaRepository<BookEntity, Long>{
    public BookEntity findByName(String name);
    @Query(value = "SELECT * FROM books WHERE author_id = :id", nativeQuery = true)
    List<BookEntity> findByAuthorId(Long id);
}
