package com.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * DTO for creating/updating products
 */
public class CreateProductRequest {
    @JsonProperty("name")
    @NotBlank(message = "Product name is required")
    private String name;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("price")
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;
    
    @JsonProperty("brand")
    @NotBlank(message = "Brand is required")
    private String brand;
    
    @JsonProperty("imageUrl")
    private String imageUrl;
    
    @JsonProperty("stockQuantity")
    @NotNull(message = "Stock quantity is required")
    private Integer stockQuantity = 0;
    
    @JsonProperty("categoryId")
    @NotNull(message = "Category ID is required")
    private Long categoryId;
    
    // Constructors
    public CreateProductRequest() {}
    
    public CreateProductRequest(String name, String description, BigDecimal price, String brand, 
                               String imageUrl, Integer stockQuantity, Long categoryId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.brand = brand;
        this.imageUrl = imageUrl;
        this.stockQuantity = stockQuantity;
        this.categoryId = categoryId;
    }
    
    // Getters and Setters
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
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public String getBrand() {
        return brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public Integer getStockQuantity() {
        return stockQuantity;
    }
    
    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
    
    public Long getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}