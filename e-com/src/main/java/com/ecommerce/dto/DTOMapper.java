package com.ecommerce.dto;

import com.ecommerce.entity.Category;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;
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
    
    /**
     * Convert Order entity to OrderDTO
     */
    public static OrderDTO toOrderDTO(Order order) {
        if (order == null) return null;
        
        List<OrderItemDTO> orderItemDTOs = order.getOrderItems() != null
            ? order.getOrderItems().stream()
                .map(DTOMapper::toOrderItemDTO)
                .collect(Collectors.toList())
            : null;
        
        return new OrderDTO(
            order.getId(),
            order.getOrderNumber(),
            order.getStatus(),
            order.getOrderDate(),
            order.getCustomerEmail(),
            order.getCustomerName(),
            order.getShippingAddress(),
            order.getTotalAmount(),
            orderItemDTOs
        );
    }
    
    /**
     * Convert Order entity to OrderSummaryDTO (without order items)
     */
    public static OrderSummaryDTO toOrderSummaryDTO(Order order) {
        if (order == null) return null;
        
        Integer totalItems = order.getOrderItems() != null 
            ? order.getOrderItems().size() 
            : 0;
        
        return new OrderSummaryDTO(
            order.getId(),
            order.getOrderNumber(),
            order.getStatus(),
            order.getOrderDate(),
            order.getCustomerEmail(),
            order.getCustomerName(),
            order.getShippingAddress(),
            order.getTotalAmount(),
            totalItems
        );
    }
    
    /**
     * Convert OrderItem entity to OrderItemDTO
     */
    public static OrderItemDTO toOrderItemDTO(OrderItem orderItem) {
        if (orderItem == null) return null;
        
        ProductSummaryDTO productDTO = toProductSummaryDTO(orderItem.getProduct());
        
        return new OrderItemDTO(
            orderItem.getId(),
            productDTO,
            orderItem.getQuantity(),
            orderItem.getUnitPrice(),
            orderItem.getSubtotal()
        );
    }
    
    /**
     * Convert list of Order entities to OrderDTO list
     */
    public static List<OrderDTO> toOrderDTOList(List<Order> orders) {
        if (orders == null) return null;
        
        return orders.stream()
            .map(DTOMapper::toOrderDTO)
            .collect(Collectors.toList());
    }
    
    /**
     * Convert list of Order entities to OrderSummaryDTO list
     */
    public static List<OrderSummaryDTO> toOrderSummaryDTOList(List<Order> orders) {
        if (orders == null) return null;
        
        return orders.stream()
            .map(DTOMapper::toOrderSummaryDTO)
            .collect(Collectors.toList());
    }
}