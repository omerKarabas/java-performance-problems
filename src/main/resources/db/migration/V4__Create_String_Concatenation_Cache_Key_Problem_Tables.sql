-- Migration: V4__Create_String_Concatenation_Cache_Key_Problem_Tables.sql
-- Description: Creates tables for demonstrating cache key string concatenation collision problems
-- Date: 2024-12-19

-- Create string_concat_cache table for cache key string concatenation demonstration
CREATE TABLE string_concat_cache (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL,
    product_id VARCHAR(50) NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    price DECIMAL(10,2) NOT NULL,
    category VARCHAR(100) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create composite index on user_id and product_id for better query performance
CREATE INDEX idx_string_concat_user_product ON string_concat_cache(user_id, product_id);

-- Create index on category for filtering
CREATE INDEX idx_string_concat_category ON string_concat_cache(category);

-- Insert sample data for string concatenation cache key collision demonstration
-- This data demonstrates the problem where different user_id + product_id combinations
-- can produce the same cache key when concatenated without proper delimiters

-- User 1 products
INSERT INTO string_concat_cache (user_id, product_id, product_name, quantity, price, category, active) VALUES
('user123', 'product45', 'MacBook Pro 16"', 1, 2499.99, 'LAPTOP', TRUE),
('user123', 'product67', 'iPhone 15 Pro', 1, 999.99, 'SMARTPHONE', TRUE),
('user123', 'product89', 'AirPods Pro', 2, 249.99, 'HEADPHONES', TRUE),
('user123', 'product12', 'Mechanical Keyboard', 1, 129.99, 'KEYBOARD', TRUE);

-- User 2 products (demonstrates collision potential)
INSERT INTO string_concat_cache (user_id, product_id, product_name, quantity, price, category, active) VALUES
('user12', '3product45', 'Dell XPS 15"', 1, 1899.99, 'LAPTOP', TRUE),
('user12', '3product67', 'Samsung Galaxy S24', 1, 899.99, 'SMARTPHONE', TRUE),
('user12', '3product89', 'Sony WH-1000XM5', 1, 349.99, 'HEADPHONES', TRUE),
('user12', '3product12', 'Logitech MX Master', 1, 99.99, 'MOUSE', TRUE);

-- User 3 products (more collision examples)
INSERT INTO string_concat_cache (user_id, product_id, product_name, quantity, price, category, active) VALUES
('user1', '23product45', 'Lenovo ThinkPad X1', 1, 2199.99, 'LAPTOP', TRUE),
('user1', '23product67', 'Google Pixel 8', 1, 699.99, 'SMARTPHONE', TRUE),
('user1', '23product89', 'Bose QuietComfort', 1, 279.99, 'HEADPHONES', TRUE),
('user1', '23product12', 'Corsair K100', 1, 199.99, 'KEYBOARD', TRUE);

-- User 4 products (demonstrates safe delimiter usage)
INSERT INTO string_concat_cache (user_id, product_id, product_name, quantity, price, category, active) VALUES
('user456', 'prod789', 'iPad Pro 12.9"', 1, 1099.99, 'TABLET', TRUE),
('user456', 'prod101', 'Microsoft Surface Pro', 1, 1299.99, 'TABLET', TRUE),
('user456', 'prod202', 'Samsung Galaxy Tab S9', 1, 799.99, 'TABLET', TRUE),
('user456', 'prod303', 'Apple Watch Series 9', 1, 399.99, 'WEARABLE', TRUE);

-- User 5 products (hash-based key examples)
INSERT INTO string_concat_cache (user_id, product_id, product_name, quantity, price, category, active) VALUES
('user789', 'item001', 'Dell UltraSharp 27"', 1, 599.99, 'MONITOR', TRUE),
('user789', 'item002', 'HP LaserJet Pro', 1, 199.99, 'PRINTER', TRUE),
('user789', 'item003', 'Canon EOS R6', 1, 2499.99, 'CAMERA', TRUE),
('user789', 'item004', 'Logitech StreamCam', 1, 169.99, 'WEBCAM', TRUE);

-- Edge cases for collision demonstration
INSERT INTO string_concat_cache (user_id, product_id, product_name, quantity, price, category, active) VALUES
('user', '123product456', 'Generic Product A', 1, 99.99, 'GENERIC', TRUE),
('user123', 'product456', 'Generic Product B', 1, 149.99, 'GENERIC', TRUE),
('user123product', '456', 'Generic Product C', 1, 199.99, 'GENERIC', TRUE);

-- Example collision scenarios documented in comments:
-- 1. user123 + product45 = user123product45
-- 2. user12 + 3product45 = user123product45 (SAME KEY!)
-- 3. user1 + 23product45 = user123product45 (SAME KEY!)
-- 4. user + 123product456 = user123product456
-- 5. user123 + product456 = user123product456 (SAME KEY!)
-- 6. user123product + 456 = user123product456 (SAME KEY!)

-- Additional collision examples for better demonstration:
-- 7. user123 + product67 = user123product67
-- 8. user12 + 3product67 = user123product67 (SAME KEY!)
-- 9. user1 + 23product67 = user123product67 (SAME KEY!)
