package com.ecommerce.dto;

import com.ecommerce.entity.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for Order entity to prevent circular reference issues
 */
public class OrderDTO {
    private Long id;
    private String orderNumber;
    private OrderStatus status;
    private LocalDateTime orderDate;
    private String customerEmail;
    private String customerName;
    private String shippingAddress;
    private BigDecimal totalAmount;
    private List<OrderItemDTO> orderItems;

    // Constructors
    public OrderDTO() {}

    public OrderDTO(Long id, String orderNumber, OrderStatus status, LocalDateTime orderDate,
                   String customerEmail, String customerName, String shippingAddress,
                   BigDecimal totalAmount, List<OrderItemDTO> orderItems) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.status = status;
        this.orderDate = orderDate;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
        this.shippingAddress = shippingAddress;
        this.totalAmount = totalAmount;
        this.orderItems = orderItems;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }
}