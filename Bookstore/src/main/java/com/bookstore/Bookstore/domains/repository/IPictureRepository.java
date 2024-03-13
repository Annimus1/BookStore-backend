package com.bookstore.Bookstore.domains.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.Bookstore.domains.models.PictureEntity;

public interface IPictureRepository extends JpaRepository<PictureEntity, Long>{
    
}
