-- Insert Categories
INSERT INTO categories (id, name, description) VALUES (1, 'Electronics', 'Electronic devices and accessories');
INSERT INTO categories (id, name, description) VALUES (2, 'Clothing', 'Fashionable clothing and accessories');
INSERT INTO categories (id, name, description) VALUES (3, 'Books', 'Books and educational materials');
INSERT INTO categories (id, name, description) VALUES (4, 'Home & Kitchen', 'Home appliances and kitchen essentials');
INSERT INTO categories (id, name, description) VALUES (5, 'Sports & Fitness', 'Sports equipment and fitness gear');

-- Insert Products - Electronics
INSERT INTO products (id, name, description, price, brand, image_url, stock_quantity, category_id) VALUES (1, 'Smartphone Pro Max', 'Latest flagship smartphone with advanced camera system', 999.99, 'TechBrand', 'https://example.com/images/smartphone.jpg', 50, 1);
INSERT INTO products (id, name, description, price, brand, image_url, stock_quantity, category_id) VALUES (2, 'Wireless Headphones', 'Premium noise-cancelling wireless headphones', 299.99, 'AudioTech', 'https://example.com/images/headphones.jpg', 100, 1);
INSERT INTO products (id, name, description, price, brand, image_url, stock_quantity, category_id) VALUES (3, 'Laptop Gaming Elite', 'High-performance gaming laptop with RTX graphics', 1599.99, 'GamerTech', 'https://example.com/images/laptop.jpg', 25, 1);
INSERT INTO products (id, name, description, price, brand, image_url, stock_quantity, category_id) VALUES (4, 'Smart Watch Ultra', 'Advanced fitness tracking and health monitoring', 399.99, 'WearTech', 'https://example.com/images/smartwatch.jpg', 75, 1);

-- Insert Products - Clothing
INSERT INTO products (id, name, description, price, brand, image_url, stock_quantity, category_id) VALUES (5, 'Premium Cotton T-Shirt', 'Comfortable 100% cotton t-shirt in various colors', 29.99, 'FashionCo', 'https://example.com/images/tshirt.jpg', 200, 2);
INSERT INTO products (id, name, description, price, brand, image_url, stock_quantity, category_id) VALUES (6, 'Designer Jeans Classic', 'Premium denim jeans with perfect fit', 89.99, 'DenimBrand', 'https://example.com/images/jeans.jpg', 150, 2);
INSERT INTO products (id, name, description, price, brand, image_url, stock_quantity, category_id) VALUES (7, 'Winter Jacket Pro', 'Waterproof and windproof winter jacket', 149.99, 'OutdoorGear', 'https://example.com/images/jacket.jpg', 80, 2);

-- Insert Products - Books
INSERT INTO products (id, name, description, price, brand, image_url, stock_quantity, category_id) VALUES (8, 'The Art of Programming', 'Comprehensive guide to software development', 45.99, 'TechPublish', 'https://example.com/images/programming-book.jpg', 300, 3);
INSERT INTO products (id, name, description, price, brand, image_url, stock_quantity, category_id) VALUES (9, 'Modern Web Development', 'Learn the latest web development technologies', 39.99, 'WebBooks', 'https://example.com/images/web-dev-book.jpg', 250, 3);
INSERT INTO products (id, name, description, price, brand, image_url, stock_quantity, category_id) VALUES (10, 'Data Science Fundamentals', 'Essential concepts in data science and analytics', 52.99, 'DataPress', 'https://example.com/images/datascience-book.jpg', 180, 3);

-- Insert Products - Home & Kitchen
INSERT INTO products (id, name, description, price, brand, image_url, stock_quantity, category_id) VALUES (11, 'Coffee Maker Deluxe', 'Programmable coffee maker with thermal carafe', 129.99, 'BrewMaster', 'https://example.com/images/coffee-maker.jpg', 60, 4);
INSERT INTO products (id, name, description, price, brand, image_url, stock_quantity, category_id) VALUES (12, 'Air Fryer Pro', 'Large capacity air fryer with digital controls', 159.99, 'KitchenTech', 'https://example.com/images/air-fryer.jpg', 45, 4);
INSERT INTO products (id, name, description, price, brand, image_url, stock_quantity, category_id) VALUES (13, 'Blender Power Max', 'High-speed blender for smoothies and food prep', 199.99, 'BlendCorp', 'https://example.com/images/blender.jpg', 35, 4);

-- Insert Products - Sports & Fitness
INSERT INTO products (id, name, description, price, brand, image_url, stock_quantity, category_id) VALUES (14, 'Yoga Mat Premium', 'Non-slip eco-friendly yoga mat', 49.99, 'YogaLife', 'https://example.com/images/yoga-mat.jpg', 120, 5);
INSERT INTO products (id, name, description, price, brand, image_url, stock_quantity, category_id) VALUES (15, 'Dumbbell Set Adjustable', 'Adjustable dumbbell set 5-50 lbs', 299.99, 'FitnessPro', 'https://example.com/images/dumbbells.jpg', 40, 5);
INSERT INTO products (id, name, description, price, brand, image_url, stock_quantity, category_id) VALUES (16, 'Running Shoes Elite', 'Professional running shoes with advanced cushioning', 149.99, 'RunTech', 'https://example.com/images/running-shoes.jpg', 90, 5);

-- Insert Orders
INSERT INTO orders (id, order_number, status, order_date, customer_email, customer_name, shipping_address, total_amount) VALUES (1, 'ORD-1001', 'DELIVERED', TIMESTAMP '2024-10-01 10:00:00', 'john.doe@example.com', 'John Doe', '123 Main St, Springfield, IL 62701', 1329.98);
INSERT INTO orders (id, order_number, status, order_date, customer_email, customer_name, shipping_address, total_amount) VALUES (2, 'ORD-1002', 'SHIPPED', TIMESTAMP '2024-10-01 14:30:00', 'jane.smith@example.com', 'Jane Smith', '456 Oak Ave, Chicago, IL 60601', 179.98);
INSERT INTO orders (id, order_number, status, order_date, customer_email, customer_name, shipping_address, total_amount) VALUES (3, 'ORD-1003', 'CONFIRMED', TIMESTAMP '2024-10-02 09:15:00', 'bob.johnson@example.com', 'Bob Johnson', '789 Pine Rd, Denver, CO 80202', 745.97);
INSERT INTO orders (id, order_number, status, order_date, customer_email, customer_name, shipping_address, total_amount) VALUES (4, 'ORD-1004', 'PENDING', TIMESTAMP '2024-10-02 16:45:00', 'alice.brown@example.com', 'Alice Brown', '321 Elm St, Austin, TX 73301', 89.98);
INSERT INTO orders (id, order_number, status, order_date, customer_email, customer_name, shipping_address, total_amount) VALUES (5, 'ORD-1005', 'DELIVERED', TIMESTAMP '2024-10-03 11:20:00', 'charlie.wilson@example.com', 'Charlie Wilson', '654 Maple Dr, Seattle, WA 98101', 449.98);

-- Insert Order Items - Order 1 (John Doe)
INSERT INTO order_items (id, order_id, product_id, quantity, unit_price) VALUES (1, 1, 1, 1, 999.99);
INSERT INTO order_items (id, order_id, product_id, quantity, unit_price) VALUES (2, 1, 2, 1, 299.99);
INSERT INTO order_items (id, order_id, product_id, quantity, unit_price) VALUES (3, 1, 5, 1, 29.99);

-- Insert Order Items - Order 2 (Jane Smith)
INSERT INTO order_items (id, order_id, product_id, quantity, unit_price) VALUES (4, 2, 7, 1, 149.99);
INSERT INTO order_items (id, order_id, product_id, quantity, unit_price) VALUES (5, 2, 5, 1, 29.99);

-- Insert Order Items - Order 3 (Bob Johnson)
INSERT INTO order_items (id, order_id, product_id, quantity, unit_price) VALUES (6, 3, 3, 1, 1599.99);
INSERT INTO order_items (id, order_id, product_id, quantity, unit_price) VALUES (7, 3, 8, 1, 45.99);
INSERT INTO order_items (id, order_id, product_id, quantity, unit_price) VALUES (8, 3, 9, 1, 39.99);
INSERT INTO order_items (id, order_id, product_id, quantity, unit_price) VALUES (9, 3, 14, 2, 49.99);

-- Insert Order Items - Order 4 (Alice Brown)
INSERT INTO order_items (id, order_id, product_id, quantity, unit_price) VALUES (10, 4, 6, 1, 89.99);

-- Insert Order Items - Order 5 (Charlie Wilson)
INSERT INTO order_items (id, order_id, product_id, quantity, unit_price) VALUES (11, 5, 4, 1, 399.99);
INSERT INTO order_items (id, order_id, product_id, quantity, unit_price) VALUES (12, 5, 14, 1, 49.99);

-- Reset sequences to continue from current max values
SELECT setval('categories_id_seq', (SELECT MAX(id) FROM categories));
SELECT setval('products_id_seq', (SELECT MAX(id) FROM products));
SELECT setval('orders_id_seq', (SELECT MAX(id) FROM orders));
SELECT setval('order_items_id_seq', (SELECT MAX(id) FROM order_items));