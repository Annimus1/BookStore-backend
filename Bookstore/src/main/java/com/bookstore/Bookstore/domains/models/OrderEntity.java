package com.bookstore.Bookstore.domains.models;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private long id;
    
    @Basic
    private UUID orderNumber;

    @Column(nullable = false)
    private double totalPrice;
    

    @Temporal(TemporalType.TIMESTAMP)
    private GregorianCalendar createdAt;
    
    @Temporal(TemporalType.TIMESTAMP)
    private GregorianCalendar updatedAt;

    @OneToMany(mappedBy = "orders", fetch = FetchType.LAZY)
    private List<BookEntity> books;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private UserEntity client;
    
    // Constructor
    
    public OrderEntity() {}

    public OrderEntity(UUID orderNumber, double totalPrice, GregorianCalendar createdAt, GregorianCalendar updatedAt,
    List<BookEntity> books, UserEntity client) {
        this.orderNumber = orderNumber;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.books = books;
        this.client = client;
    }

    // Getters & Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(UUID orderNumber) {
        this.orderNumber = orderNumber;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public GregorianCalendar getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(GregorianCalendar createdAt) {
        this.createdAt = createdAt;
    }

    public GregorianCalendar getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(GregorianCalendar updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(List<BookEntity> books) {
        this.books = books;
    }

    public UserEntity getClient() {
        return client;
    }

    public void setClient(UserEntity client) {
        this.client = client;
    }

    


    
}
