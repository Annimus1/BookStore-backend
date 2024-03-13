package com.bookstore.Bookstore.domains.models;

import java.util.GregorianCalendar;
import java.util.UUID;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Column(nullable = true)
    private String books;
    @Column(nullable = true)
    private String client;
    
    //Constructor
    
    public OrderEntity() {}



    
}
