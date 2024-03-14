package com.bookstore.Bookstore.domains.dto;

public class BookRequest {
    private long author;
    private String cover; 
    private int quantity;
    private String description; 
    private String[] genres;
    private String isbn; 
    private String name; 
    private int pages;
    private double price;
    private float puntuation;
    private String releaseDate;
    
    // Constructors
    public BookRequest(long author, String cover, int quantity, String description, String[] genres, String isbn,
            String name, int pages, double price, float puntuation, String releaseDate) {
        this.author = author;
        this.cover = cover;
        this.quantity = quantity;
        this.description = description;
        this.genres = genres;
        this.isbn = isbn;
        this.name = name;
        this.pages = pages;
        this.price = price;
        this.puntuation = puntuation;
        this.releaseDate = releaseDate;
    }

    // Getters & Setters
    public long getAuthor() {
        return author;
    }

    public void setAuthor(long author) {
        this.author = author;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public float getPuntuation() {
        return puntuation;
    }

    public void setPuntuation(float puntuation) {
        this.puntuation = puntuation;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    
}
