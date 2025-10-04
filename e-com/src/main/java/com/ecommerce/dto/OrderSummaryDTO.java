package com.ecommerce.dto;

import com.ecommerce.entity.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Summary DTO for Order entity (without order items)
 */
public class OrderSummaryDTO {
    private Long id;
    private String orderNumber;
    private OrderStatus status;
    private LocalDateTime orderDate;
    private String customerEmail;
    private String customerName;
    private String shippingAddress;
    private BigDecimal totalAmount;
    private Integer totalItems;

    // Constructors
    public OrderSummaryDTO() {}

    public OrderSummaryDTO(Long id, String orderNumber, OrderStatus status, LocalDateTime orderDate,
                          String customerEmail, String customerName, String shippingAddress,
                          BigDecimal totalAmount, Integer totalItems) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.status = status;
        this.orderDate = orderDate;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
        this.shippingAddress = shippingAddress;
        this.totalAmount = totalAmount;
        this.totalItems = totalItems;
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

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }
}