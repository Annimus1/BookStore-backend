package com.bookstore.Bookstore.domains.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.Bookstore.domains.models.AuthorEntity;

public interface IAuthorRepository extends JpaRepository<AuthorEntity, Long> {
    public AuthorEntity findByName(String name);
}
