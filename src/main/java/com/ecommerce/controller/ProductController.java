package com.ecommerce.controller;

import com.ecommerce.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@Transactional
public class ProductController {
    
    @Autowired
    private EntityManager entityManager;
    
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String search) {
        
        StringBuilder jpql = new StringBuilder("SELECT p FROM Product p WHERE 1=1");
        
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
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = entityManager.find(Product.class, id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        entityManager.persist(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product productDetails) {
        Product product = entityManager.find(Product.class, id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setBrand(productDetails.getBrand());
        product.setImageUrl(productDetails.getImageUrl());
        product.setStockQuantity(productDetails.getStockQuantity());
        product.setCategory(productDetails.getCategory());
        
        Product updatedProduct = entityManager.merge(product);
        return ResponseEntity.ok(updatedProduct);
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
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long categoryId) {
        TypedQuery<Product> query = entityManager.createQuery(
            "SELECT p FROM Product p WHERE p.category.id = :categoryId", Product.class);
        query.setParameter("categoryId", categoryId);
        
        List<Product> products = query.getResultList();
        return ResponseEntity.ok(products);
    }
}