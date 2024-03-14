package com.bookstore.Bookstore.domains.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @JsonIgnore
    @ManyToMany(mappedBy = "genres")
    private List<BookEntity> books;

    // Constructors
    public GenreEntity(){}

    public GenreEntity(String name){
        this.name = name;
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

    @Override
    public String toString() {
        return String.format("""
                {"id":"%s", "name": "%s"}
                """,String.valueOf(this.getId()), this.name);
    }
}
