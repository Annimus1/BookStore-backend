package com.bookstore.Bookstore.domains.models;


import java.util.GregorianCalendar;
import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "books")
public class BookEntity {
    @Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private long id;
    @Basic    
    private String name;
    @Column(length=1024, nullable = false)
    private String description;
    @Column(nullable = false, unique = true)
    private String ISBN; 
    @Column(nullable = false)
    private int pages;
    @Column(nullable = false)
    private double price;
    @Basic
    private float puntuation;
    @Basic
    private int quantity;
    
    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private GregorianCalendar releaseDate;
    
    @OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn(name = "picture_id")
    private PictureEntity cover;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private AuthorEntity author;
   
    @ManyToMany
    @JoinTable(
        name = "book_genre",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<GenreEntity> genres;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = true)
    private OrderEntity orders;   
    // Constructors
    public BookEntity() {}

    public BookEntity(String name, String description, String iSBN, int pages, double price, float puntuation,
            int quantity, GregorianCalendar releaseDate, PictureEntity cover, AuthorEntity author, List<GenreEntity> genres,OrderEntity orders) {
        this.name = name;
        this.description = description;
        ISBN = iSBN;
        this.pages = pages;
        this.price = price;
        this.puntuation = puntuation;
        this.quantity = quantity;
        this.releaseDate = releaseDate;
        this.cover = cover;
        this.author = author;
        this.genres = genres;
        this.orders = orders;
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

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String iSBN) {
        ISBN = iSBN;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public GregorianCalendar getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(GregorianCalendar releaseDate) {
        this.releaseDate = releaseDate;
    }

    public PictureEntity getCover() {
        return cover;
    }

    public void setCover(PictureEntity cover) {
        this.cover = cover;
    }

    public AuthorEntity getAuthor() {
        return author;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }

    public List<GenreEntity> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreEntity> genres) {
        this.genres = genres;
    }

    public OrderEntity getOrders() {
        return orders;
    }

    public void setOrders(OrderEntity orders) {
        this.orders = orders;
    }

   
    
}
