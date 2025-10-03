package com.ecommerce.dto;

import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for converting between entities and DTOs
 */
public class DTOMapper {
    
    /**
     * Convert Category entity to CategoryDTO
     */
    public static CategoryDTO toCategoryDTO(Category category) {
        if (category == null) return null;
        
        List<ProductSummaryDTO> productSummaries = category.getProducts() != null 
            ? category.getProducts().stream()
                .map(DTOMapper::toProductSummaryDTO)
                .collect(Collectors.toList())
            : null;
        
        return new CategoryDTO(
            category.getId(),
            category.getName(),
            category.getDescription(),
            productSummaries
        );
    }
    
    /**
     * Convert Category entity to CategorySummaryDTO (without products)
     */
    public static CategorySummaryDTO toCategorySummaryDTO(Category category) {
        if (category == null) return null;
        
        return new CategorySummaryDTO(
            category.getId(),
            category.getName(),
            category.getDescription()
        );
    }
    
    /**
     * Convert Product entity to ProductDTO
     */
    public static ProductDTO toProductDTO(Product product) {
        if (product == null) return null;
        
        CategorySummaryDTO categoryDTO = toCategorySummaryDTO(product.getCategory());
        
        return new ProductDTO(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getBrand(),
            product.getImageUrl(),
            product.getStockQuantity(),
            categoryDTO
        );
    }
    
    /**
     * Convert Product entity to ProductSummaryDTO (without category)
     */
    public static ProductSummaryDTO toProductSummaryDTO(Product product) {
        if (product == null) return null;
        
        return new ProductSummaryDTO(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getBrand(),
            product.getImageUrl(),
            product.getStockQuantity()
        );
    }
    
    /**
     * Convert list of Category entities to CategoryDTO list
     */
    public static List<CategoryDTO> toCategoryDTOList(List<Category> categories) {
        if (categories == null) return null;
        
        return categories.stream()
            .map(DTOMapper::toCategoryDTO)
            .collect(Collectors.toList());
    }
    
    /**
     * Convert list of Product entities to ProductDTO list
     */
    public static List<ProductDTO> toProductDTOList(List<Product> products) {
        if (products == null) return null;
        
        return products.stream()
            .map(DTOMapper::toProductDTO)
            .collect(Collectors.toList());
    }
}