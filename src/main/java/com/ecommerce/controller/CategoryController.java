package com.ecommerce.controller;

import com.ecommerce.entity.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Transactional
public class CategoryController {
    
    @Autowired
    private EntityManager entityManager;
    
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        TypedQuery<Category> query = entityManager.createQuery("SELECT c FROM Category c", Category.class);
        List<Category> categories = query.getResultList();
        return ResponseEntity.ok(categories);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = entityManager.find(Category.class, id);
        return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
        entityManager.persist(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @Valid @RequestBody Category categoryDetails) {
        Category category = entityManager.find(Category.class, id);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        
        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());
        
        Category updatedCategory = entityManager.merge(category);
        return ResponseEntity.ok(updatedCategory);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        Category category = entityManager.find(Category.class, id);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        
        entityManager.remove(category);
        return ResponseEntity.noContent().build();
    }
}