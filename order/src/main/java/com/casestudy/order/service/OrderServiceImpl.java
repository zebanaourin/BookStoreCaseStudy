package com.casestudy.order.service;

import com.casestudy.order.dto.Customer;
import com.casestudy.order.entity.Order;
import com.casestudy.order.dto.Book;
import com.casestudy.order.entity.OrderStatus;
import com.casestudy.order.exception.InsufficientStockException;
import com.casestudy.order.exception.OrderNotFoundException;
import com.casestudy.order.feignClients.BookServiceClient;
import com.casestudy.order.feignClients.CustomerServiceClient;
import com.casestudy.order.repository.OrderRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{


    private final OrderRepository orderRepository;
    private final BookServiceClient bookServiceClient;
    private final CustomerServiceClient customerServiceClient;

    public OrderServiceImpl(OrderRepository orderRepository, BookServiceClient bookServiceClient, CustomerServiceClient customerServiceClient) {
        this.orderRepository = orderRepository;
        this.bookServiceClient = bookServiceClient;
        this.customerServiceClient = customerServiceClient;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id " + id));
    }

    public Order createOrder(Order order) {
        validateOrder(order);
        order.setStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, Order orderDetails) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id " + id));

        validateOrder(orderDetails);

        existingOrder.setCustomerId(orderDetails.getCustomerId());
        existingOrder.setBookId(orderDetails.getBookId());
        existingOrder.setQuantity(orderDetails.getQuantity());
        existingOrder.setStatus(orderDetails.getStatus());

        return orderRepository.save(existingOrder);
    }

    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id " + id));
        orderRepository.delete(order);
    }

    private void validateOrder(Order order) {
        // Validate customer
        Customer customer = customerServiceClient.getCustomerById(order.getCustomerId());
        if (customer == null) {
            throw new OrderNotFoundException("Customer not found with id " + order.getCustomerId());
        }

        // Validate book and stock
        Book book = bookServiceClient.getBookById(order.getBookId());
        if (book == null) {
            throw new OrderNotFoundException("Book not found with id " + order.getBookId());
        }

        if (book.getStock() < order.getQuantity()) {
            throw new InsufficientStockException("Not enough stock available for book id " + order.getBookId());
        }
    }
}