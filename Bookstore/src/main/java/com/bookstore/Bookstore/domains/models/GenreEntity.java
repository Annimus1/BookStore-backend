package com.bookstore.Bookstore.domains.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "genres")
public class GenreEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    
    @Column(length = 20, unique = true, nullable = false)
    private String name; 

    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    private List<BookEntity> books;
    
    // Constructors
    public GenreEntity(){}

    public GenreEntity(String name){
        this.name = name;
    }

    public GenreEntity(String name, List<BookEntity> books){
        this.name = name;
        this.books = books;
    }

    // Getters & Setter
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

    public List<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(List<BookEntity> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return String.format("""
                {"id":"%s", "name": "%s"}
                """,String.valueOf(this.getId()), this.name);
    }
}
