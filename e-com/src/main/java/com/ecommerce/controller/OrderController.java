package com.ecommerce.controller;

import com.ecommerce.dto.DTOMapper;
import com.ecommerce.dto.OrderDTO;
import com.ecommerce.dto.OrderSummaryDTO;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.entity.OrderStatus;
import com.ecommerce.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@Transactional
public class OrderController {
    
    @Autowired
    private EntityManager entityManager;
    
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders(
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = true) String customerEmail) {
        
        // Validate customerEmail is not empty
        if (customerEmail == null || customerEmail.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        StringBuilder jpql = new StringBuilder("SELECT o FROM Order o WHERE o.customerEmail = :customerEmail");
        
        if (status != null) {
            jpql.append(" AND o.status = :status");
        }
        jpql.append(" ORDER BY o.orderDate DESC");
        
        TypedQuery<Order> query = entityManager.createQuery(jpql.toString(), Order.class);
        query.setParameter("customerEmail", customerEmail.trim());
        
        if (status != null) {
            query.setParameter("status", status);
        }
        
        List<Order> orders = query.getResultList();
        List<OrderDTO> orderDTOs = DTOMapper.toOrderDTOList(orders);
        return ResponseEntity.ok(orderDTOs);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        Order order = entityManager.find(Order.class, id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        OrderDTO orderDTO = DTOMapper.toOrderDTO(order);
        return ResponseEntity.ok(orderDTO);
    }
    
    @GetMapping("/number/{orderNumber}")
    public ResponseEntity<OrderDTO> getOrderByNumber(@PathVariable String orderNumber) {
        TypedQuery<Order> query = entityManager.createQuery(
            "SELECT o FROM Order o WHERE o.orderNumber = :orderNumber", Order.class);
        query.setParameter("orderNumber", orderNumber);
        
        List<Order> orders = query.getResultList();
        if (orders.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        OrderDTO orderDTO = DTOMapper.toOrderDTO(orders.get(0));
        return ResponseEntity.ok(orderDTO);
    }
    
    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        try {
            // Create new order
            Order order = new Order(
                request.getCustomerEmail(),
                request.getCustomerName(),
                request.getShippingAddress()
            );
            
            // Add order items
            for (var itemRequest : request.getOrderItems()) {
                Product product = entityManager.find(Product.class, itemRequest.getProductId());
                if (product == null) {
                    return ResponseEntity.badRequest()
                        .body(Map.of("error", "Product not found with ID: " + itemRequest.getProductId()));
                }
                
                // Check stock availability
                if (product.getStockQuantity() < itemRequest.getQuantity()) {
                    return ResponseEntity.badRequest()
                        .body(Map.of("error", "Insufficient stock for product: " + product.getName()));
                }
                
                OrderItem orderItem = new OrderItem(
                    product,
                    itemRequest.getQuantity(),
                    product.getPrice()
                );
                
                order.addOrderItem(orderItem);
                
                // Update stock
                product.setStockQuantity(product.getStockQuantity() - itemRequest.getQuantity());
                entityManager.merge(product);
            }
            
            entityManager.persist(order);
            OrderDTO orderDTO = DTOMapper.toOrderDTO(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Failed to create order: " + e.getMessage()));
        }
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        Order order = entityManager.find(Order.class, id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        
        order.setStatus(status);
        Order updatedOrder = entityManager.merge(order);
        OrderDTO orderDTO = DTOMapper.toOrderDTO(updatedOrder);
        return ResponseEntity.ok(orderDTO);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id) {
        Order order = entityManager.find(Order.class, id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        
        // Only allow cancellation of pending or confirmed orders
        if (order.getStatus() == OrderStatus.SHIPPED || order.getStatus() == OrderStatus.DELIVERED) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Cannot cancel order with status: " + order.getStatus()));
        }
        
        // Restore stock quantities
        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
            entityManager.merge(product);
        }
        
        // Update order status to cancelled
        order.setStatus(OrderStatus.CANCELLED);
        entityManager.merge(order);
        
        return ResponseEntity.ok(Map.of("message", "Order cancelled successfully"));
    }
    
    // Simple DTOs as inner classes
    public static class CreateOrderRequest {
        private String customerEmail;
        private String customerName;
        private String shippingAddress;
        private List<CreateOrderItemRequest> orderItems;
        
        // Getters and Setters
        public String getCustomerEmail() { return customerEmail; }
        public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
        
        public String getCustomerName() { return customerName; }
        public void setCustomerName(String customerName) { this.customerName = customerName; }
        
        public String getShippingAddress() { return shippingAddress; }
        public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
        
        public List<CreateOrderItemRequest> getOrderItems() { return orderItems; }
        public void setOrderItems(List<CreateOrderItemRequest> orderItems) { this.orderItems = orderItems; }
    }
    
    public static class CreateOrderItemRequest {
        private Long productId;
        private Integer quantity;
        
        // Getters and Setters
        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }
        
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }
}