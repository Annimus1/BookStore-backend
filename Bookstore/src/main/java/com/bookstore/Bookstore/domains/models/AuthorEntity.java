package com.bookstore.Bookstore.domains.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "authors")
public class AuthorEntity {
    @Id @GeneratedValue(strategy=GenerationType.SEQUENCE) 
    private long id;
    
    @Column(unique=true)
    private String name;
    
    @Column(length=1024)
    private String description;
    
    @OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private PictureEntity image;

    private String books;
    
    // Constructors

    public AuthorEntity() {}

    public AuthorEntity(long id, String name, String description, PictureEntity image, String books) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.books = books;
    }

    public AuthorEntity(String name, String description, PictureEntity image, String books) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.books = books;
    }

    // Getters & Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PictureEntity getImage() {
        return image;
    }

    public void setImage(PictureEntity image) {
        this.image = image;
    }

    public String getBooks() {
        return books;
    }

    public void setBooks(String books) {
        this.books = books;
    }
    
}
