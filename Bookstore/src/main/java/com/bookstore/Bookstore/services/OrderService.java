package com.bookstore.Bookstore.services;

import java.util.Optional;
import java.util.UUID;

import com.bookstore.Bookstore.domains.models.OrderEntity;
import com.bookstore.Bookstore.domains.repository.IOrderRepository;

public class OrderService {
    IOrderRepository orderRepository;

    public OrderService(IOrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    // Create
    public OrderEntity createOrder(OrderEntity order){
        return this.orderRepository.save(order);
    }

    // Delete
    public void deleteOrder(OrderEntity order){
        this.orderRepository.delete(order);
    }

    public void deleteOrderById(long id){
        this.orderRepository.deleteById(id);
    }

    // Get
    public Optional<OrderEntity> getById(long id){
        return this.orderRepository.findById(id);
    }
    
    public OrderEntity getByName(UUID uuid){
        return this.orderRepository.findByOrderNumber(uuid);
    }
}
