package com.bookstore.Bookstore.domains.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.Bookstore.domains.models.OrderEntity;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long>{
    public OrderEntity findByOrderNumber(UUID uuid);
}
