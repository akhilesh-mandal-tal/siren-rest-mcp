package com.ecommerce.controller;

import com.ecommerce.entity.Category;
import com.ecommerce.dto.CategoryDTO;
import com.ecommerce.dto.CategorySummaryDTO;
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

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Transactional
public class CategoryController {
    
    @Autowired
    private EntityManager entityManager;
    
    @GetMapping
    public ResponseEntity<?> getAllCategories(@RequestParam(required = false, defaultValue = "false") boolean includeProducts) {
        if (includeProducts) {
            // Return categories with products
            TypedQuery<Category> query = entityManager.createQuery("SELECT c FROM Category c LEFT JOIN FETCH c.products", Category.class);
            List<Category> categories = query.getResultList();
            List<CategoryDTO> categoryDTOs = DTOMapper.toCategoryDTOList(categories);
            return ResponseEntity.ok(categoryDTOs);
        } else {
            // Return categories without products (summary only)
            TypedQuery<Category> query = entityManager.createQuery("SELECT c FROM Category c", Category.class);
            List<Category> categories = query.getResultList();
            List<CategorySummaryDTO> categoryDTOs = categories.stream()
                .map(DTOMapper::toCategorySummaryDTO)
                .collect(java.util.stream.Collectors.toList());
            return ResponseEntity.ok(categoryDTOs);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        TypedQuery<Category> query = entityManager.createQuery(
            "SELECT c FROM Category c LEFT JOIN FETCH c.products WHERE c.id = :id", Category.class);
        query.setParameter("id", id);
        
        List<Category> categories = query.getResultList();
        if (categories.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        CategoryDTO categoryDTO = DTOMapper.toCategoryDTO(categories.get(0));
        return ResponseEntity.ok(categoryDTO);
    }
    
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody Category category) {
        entityManager.persist(category);
        entityManager.flush(); // Ensure the entity is persisted and has an ID
        
        // Fetch the category with products for DTO conversion
        TypedQuery<Category> query = entityManager.createQuery(
            "SELECT c FROM Category c LEFT JOIN FETCH c.products WHERE c.id = :id", Category.class);
        query.setParameter("id", category.getId());
        
        Category savedCategory = query.getResultList().get(0);
        CategoryDTO categoryDTO = DTOMapper.toCategoryDTO(savedCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDTO);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody Category categoryDetails) {
        Category category = entityManager.find(Category.class, id);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        
        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());
        
        Category updatedCategory = entityManager.merge(category);
        entityManager.flush();
        
        // Fetch the updated category with products for DTO conversion
        TypedQuery<Category> query = entityManager.createQuery(
            "SELECT c FROM Category c LEFT JOIN FETCH c.products WHERE c.id = :id", Category.class);
        query.setParameter("id", id);
        
        Category fetchedCategory = query.getResultList().get(0);
        CategoryDTO categoryDTO = DTOMapper.toCategoryDTO(fetchedCategory);
        return ResponseEntity.ok(categoryDTO);
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