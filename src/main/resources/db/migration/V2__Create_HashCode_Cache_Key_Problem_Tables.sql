-- Migration: V2__Create_HashCode_Cache_Key_Problem_Tables.sql
-- Description: Creates tables for demonstrating cache key hashCode mismatch problems
-- Date: 2025-08-23

-- Create products table for cache key hashCode demonstration
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock_quantity INT NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create index on category for better query performance
CREATE INDEX idx_products_category ON products(category);

-- Create index on code for unique constraint
CREATE INDEX idx_products_code ON products(code);

-- Insert sample data for cache hashCode demonstration
INSERT INTO products (code, name, category, price, stock_quantity, active) VALUES
('LAP001', 'MacBook Pro 16"', 'LAPTOP', 2499.99, 10, TRUE),
('LAP002', 'Dell XPS 15"', 'LAPTOP', 1899.99, 15, TRUE),
('LAP003', 'Lenovo ThinkPad X1', 'LAPTOP', 2199.99, 8, TRUE),
('DES001', 'iMac 24"', 'DESKTOP', 1299.99, 12, TRUE),
('DES002', 'Dell OptiPlex', 'DESKTOP', 899.99, 18, TRUE),
('TAB001', 'iPad Pro 12.9"', 'TABLET', 1099.99, 12, TRUE),
('TAB002', 'Samsung Galaxy Tab S9', 'TABLET', 799.99, 14, TRUE),
('TAB003', 'Microsoft Surface Pro', 'TABLET', 1299.99, 9, TRUE),
('PHN001', 'iPhone 15 Pro', 'SMARTPHONE', 999.99, 25, TRUE),
('PHN002', 'Samsung Galaxy S24', 'SMARTPHONE', 899.99, 20, TRUE),
('PHN003', 'Google Pixel 8', 'SMARTPHONE', 699.99, 18, TRUE),
('MON001', 'Dell UltraSharp 27"', 'MONITOR', 599.99, 15, TRUE),
('KEY001', 'Mechanical Keyboard RGB', 'KEYBOARD', 129.99, 30, TRUE),
('MOU001', 'Wireless Gaming Mouse', 'MOUSE', 79.99, 25, TRUE),
('HEA001', 'AirPods Pro', 'HEADPHONES', 249.99, 30, TRUE),
('CAM001', 'Sony Alpha A7 IV', 'CAMERA', 2499.99, 8, TRUE),
('PRI001', 'HP LaserJet Pro', 'PRINTER', 199.99, 20, TRUE);

-- Add comments for documentation
COMMENT ON TABLE products IS 'Products table for demonstrating cache key hashCode mismatch problems and solutions';
COMMENT ON COLUMN products.code IS 'Unique product code used for identification';
COMMENT ON COLUMN products.category IS 'Product category for grouping and filtering (enum values)';
COMMENT ON COLUMN products.price IS 'Product price with 2 decimal precision using BigDecimal';
COMMENT ON COLUMN products.stock_quantity IS 'Available stock quantity';
COMMENT ON COLUMN products.active IS 'Whether the product is currently active';
