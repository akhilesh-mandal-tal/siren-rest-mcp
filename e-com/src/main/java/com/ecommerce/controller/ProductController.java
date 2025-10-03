package com.ecommerce.controller;

import com.ecommerce.entity.Product;
import com.ecommerce.entity.Category;
import com.ecommerce.dto.ProductDTO;
import com.ecommerce.dto.ProductSummaryDTO;
import com.ecommerce.dto.CreateProductRequest;
import com.ecommerce.dto.DTOMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@Transactional
public class ProductController {
    
    @Autowired
    private EntityManager entityManager;
    
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String search) {
        
        StringBuilder jpql = new StringBuilder("SELECT p FROM Product p JOIN FETCH p.category WHERE 1=1");
        
        if (categoryId != null) {
            jpql.append(" AND p.category.id = :categoryId");
        }
        if (brand != null && !brand.trim().isEmpty()) {
            jpql.append(" AND LOWER(p.brand) LIKE LOWER(:brand)");
        }
        if (minPrice != null) {
            jpql.append(" AND p.price >= :minPrice");
        }
        if (maxPrice != null) {
            jpql.append(" AND p.price <= :maxPrice");
        }
        if (search != null && !search.trim().isEmpty()) {
            jpql.append(" AND LOWER(p.name) LIKE LOWER(:search)");
        }
        
        TypedQuery<Product> query = entityManager.createQuery(jpql.toString(), Product.class);
        
        if (categoryId != null) {
            query.setParameter("categoryId", categoryId);
        }
        if (brand != null && !brand.trim().isEmpty()) {
            query.setParameter("brand", "%" + brand.trim() + "%");
        }
        if (minPrice != null) {
            query.setParameter("minPrice", minPrice);
        }
        if (maxPrice != null) {
            query.setParameter("maxPrice", maxPrice);
        }
        if (search != null && !search.trim().isEmpty()) {
            query.setParameter("search", "%" + search.trim() + "%");
        }
        
        List<Product> products = query.getResultList();
        List<ProductDTO> productDTOs = DTOMapper.toProductDTOList(products);
        return ResponseEntity.ok(productDTOs);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        TypedQuery<Product> query = entityManager.createQuery(
            "SELECT p FROM Product p JOIN FETCH p.category WHERE p.id = :id", Product.class);
        query.setParameter("id", id);
        
        List<Product> products = query.getResultList();
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        ProductDTO productDTO = DTOMapper.toProductDTO(products.get(0));
        return ResponseEntity.ok(productDTO);
    }
    
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody CreateProductRequest request) {
        // Find the category
        Category category = entityManager.find(Category.class, request.getCategoryId());
        if (category == null) {
            return ResponseEntity.badRequest().build(); // Category not found
        }
        
        // Create the product entity
        Product product = new Product(
            request.getName(),
            request.getDescription(),
            request.getPrice(),
            request.getBrand(),
            category,
            request.getStockQuantity()
        );
        product.setImageUrl(request.getImageUrl());
        
        entityManager.persist(product);
        entityManager.flush(); // Ensure the entity is persisted and has an ID
        
        // Fetch the product with category for DTO conversion
        TypedQuery<Product> query = entityManager.createQuery(
            "SELECT p FROM Product p JOIN FETCH p.category WHERE p.id = :id", Product.class);
        query.setParameter("id", product.getId());
        
        Product savedProduct = query.getResultList().get(0);
        ProductDTO productDTO = DTOMapper.toProductDTO(savedProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDTO);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody CreateProductRequest request) {
        Product product = entityManager.find(Product.class, id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        
        // Find the category if it's being changed
        Category category = entityManager.find(Category.class, request.getCategoryId());
        if (category == null) {
            return ResponseEntity.badRequest().build(); // Category not found
        }
        
        // Update the product
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setBrand(request.getBrand());
        product.setImageUrl(request.getImageUrl());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(category);
        
        Product updatedProduct = entityManager.merge(product);
        entityManager.flush();
        
        // Fetch the updated product with category for DTO conversion
        TypedQuery<Product> query = entityManager.createQuery(
            "SELECT p FROM Product p JOIN FETCH p.category WHERE p.id = :id", Product.class);
        query.setParameter("id", id);
        
        Product fetchedProduct = query.getResultList().get(0);
        ProductDTO productDTO = DTOMapper.toProductDTO(fetchedProduct);
        return ResponseEntity.ok(productDTO);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        Product product = entityManager.find(Product.class, id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        
        entityManager.remove(product);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getProductsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(required = false, defaultValue = "false") boolean includeCategory) {
        
        if (includeCategory) {
            // Return full products with category info
            TypedQuery<Product> query = entityManager.createQuery(
                "SELECT p FROM Product p JOIN FETCH p.category WHERE p.category.id = :categoryId", Product.class);
            query.setParameter("categoryId", categoryId);
            
            List<Product> products = query.getResultList();
            List<ProductDTO> productDTOs = DTOMapper.toProductDTOList(products);
            return ResponseEntity.ok(productDTOs);
        } else {
            // Return product summaries without category info (since we already know the category)
            TypedQuery<Product> query = entityManager.createQuery(
                "SELECT p FROM Product p WHERE p.category.id = :categoryId", Product.class);
            query.setParameter("categoryId", categoryId);
            
            List<Product> products = query.getResultList();
            List<ProductSummaryDTO> productDTOs = products.stream()
                .map(DTOMapper::toProductSummaryDTO)
                .collect(java.util.stream.Collectors.toList());
            return ResponseEntity.ok(productDTOs);
        }
    }
}