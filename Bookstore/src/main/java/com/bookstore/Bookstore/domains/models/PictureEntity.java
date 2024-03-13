package com.bookstore.Bookstore.domains.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pictures")
public class PictureEntity {
    @Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private long id;

    @Column( name = "path", length=1024)
    private String path;

    // Constructors
    public PictureEntity() {}

    public PictureEntity(String path){
        this.path = path;
    }

    // Getters & Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    

}
