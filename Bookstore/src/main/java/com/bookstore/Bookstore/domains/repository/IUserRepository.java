package com.bookstore.Bookstore.domains.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.Bookstore.domains.models.UserEntity;

public interface IUserRepository extends JpaRepository<UserEntity,Long>{
    public UserEntity findByEmail(String email);
}
