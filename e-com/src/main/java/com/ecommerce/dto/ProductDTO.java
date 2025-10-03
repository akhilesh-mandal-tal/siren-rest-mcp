package com.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * Product DTO that includes category summary to avoid circular references
 */
public class ProductDTO {
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("price")
    private BigDecimal price;
    
    @JsonProperty("brand")
    private String brand;
    
    @JsonProperty("imageUrl")
    private String imageUrl;
    
    @JsonProperty("stockQuantity")
    private Integer stockQuantity;
    
    @JsonProperty("category")
    private CategorySummaryDTO category;
    
    // Constructors
    public ProductDTO() {}
    
    public ProductDTO(Long id, String name, String description, BigDecimal price, 
                     String brand, String imageUrl, Integer stockQuantity, 
                     CategorySummaryDTO category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.brand = brand;
        this.imageUrl = imageUrl;
        this.stockQuantity = stockQuantity;
        this.category = category;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
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
    
    public CategorySummaryDTO getCategory() {
        return category;
    }
    
    public void setCategory(CategorySummaryDTO category) {
        this.category = category;
    }
}