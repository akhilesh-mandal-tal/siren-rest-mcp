package com.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Category DTO that includes products but without circular references
 */
public class CategoryDTO {
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("products")
    private List<ProductSummaryDTO> products;
    
    // Constructors
    public CategoryDTO() {}
    
    public CategoryDTO(Long id, String name, String description, List<ProductSummaryDTO> products) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.products = products;
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
    
    public List<ProductSummaryDTO> getProducts() {
        return products;
    }
    
    public void setProducts(List<ProductSummaryDTO> products) {
        this.products = products;
    }
}