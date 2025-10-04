package com.ecommerce.dto;

import java.math.BigDecimal;

/**
 * DTO for OrderItem entity to prevent circular reference issues
 */
public class OrderItemDTO {
    private Long id;
    private ProductSummaryDTO product;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;

    // Constructors
    public OrderItemDTO() {}

    public OrderItemDTO(Long id, ProductSummaryDTO product, Integer quantity, 
                       BigDecimal unitPrice, BigDecimal subtotal) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductSummaryDTO getProduct() {
        return product;
    }

    public void setProduct(ProductSummaryDTO product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}