-- Migration: V3__Create_Timestamp_Cache_Key_Problem_Tables.sql
-- Description: Creates tables for demonstrating timestamp-based cache key problems
-- Date: 2025-08-23

-- Create timestamp_cache_orders table for timestamp cache key demonstration
CREATE TABLE timestamp_cache_orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_number VARCHAR(50) NOT NULL UNIQUE,
    customer_name VARCHAR(255) NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL,
    order_date TIMESTAMP NOT NULL,
    status VARCHAR(100) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create index on customer_name and status for better query performance
CREATE INDEX idx_timestamp_cache_orders_customer_status ON timestamp_cache_orders(customer_name, status);

-- Create index on order_number for unique constraint
CREATE INDEX idx_timestamp_cache_orders_number ON timestamp_cache_orders(order_number);

-- Insert sample data for timestamp cache key demonstration
INSERT INTO timestamp_cache_orders (order_number, customer_name, product_name, amount, quantity, order_date, status, active) VALUES
('ORD-001', 'John Doe', 'MacBook Pro 16"', 2499.99, 1, '2025-08-23 10:00:00', 'COMPLETED', TRUE),
('ORD-002', 'John Doe', 'iPhone 15 Pro', 999.99, 1, '2025-08-23 10:15:00', 'COMPLETED', TRUE),
('ORD-003', 'John Doe', 'AirPods Pro', 249.99, 1, '2025-08-23 10:30:00', 'PENDING', TRUE),
('ORD-004', 'Jane Smith', 'Dell XPS 15"', 1899.99, 1, '2025-08-23 11:00:00', 'COMPLETED', TRUE),
('ORD-005', 'Jane Smith', 'Samsung Galaxy S24', 899.99, 1, '2025-08-23 11:15:00', 'COMPLETED', TRUE),
('ORD-006', 'Jane Smith', 'iPad Pro 12.9"', 1099.99, 1, '2025-08-23 11:30:00', 'PENDING', TRUE),
('ORD-007', 'Bob Johnson', 'Lenovo ThinkPad X1', 2199.99, 1, '2025-08-23 12:00:00', 'COMPLETED', TRUE),
('ORD-008', 'Bob Johnson', 'Google Pixel 8', 699.99, 1, '2025-08-23 12:15:00', 'COMPLETED', TRUE),
('ORD-009', 'Bob Johnson', 'Mechanical Keyboard RGB', 129.99, 1, '2025-08-23 12:30:00', 'PENDING', TRUE),
('ORD-010', 'Alice Brown', 'iMac 24"', 1299.99, 1, '2025-08-23 13:00:00', 'COMPLETED', TRUE),
('ORD-011', 'Alice Brown', 'Samsung Galaxy Tab S9', 799.99, 1, '2025-08-23 13:15:00', 'COMPLETED', TRUE),
('ORD-012', 'Alice Brown', 'Wireless Gaming Mouse', 79.99, 1, '2025-08-23 13:30:00', 'PENDING', TRUE),
('ORD-013', 'Charlie Wilson', 'Dell UltraSharp 27"', 599.99, 1, '2025-08-23 14:00:00', 'COMPLETED', TRUE),
('ORD-014', 'Charlie Wilson', 'Sony Alpha A7 IV', 2499.99, 1, '2025-08-23 14:15:00', 'COMPLETED', TRUE),
('ORD-015', 'Charlie Wilson', 'HP LaserJet Pro', 199.99, 1, '2025-08-23 14:30:00', 'PENDING', TRUE);

-- Add comments for documentation
COMMENT ON TABLE timestamp_cache_orders IS 'Timestamp cache orders table for demonstrating timestamp-based cache key problems and solutions';
COMMENT ON COLUMN timestamp_cache_orders.order_number IS 'Unique order number used for identification';
COMMENT ON COLUMN timestamp_cache_orders.customer_name IS 'Customer name for grouping and filtering';
COMMENT ON COLUMN timestamp_cache_orders.product_name IS 'Product name in the order';
COMMENT ON COLUMN timestamp_cache_orders.amount IS 'Order amount with 2 decimal precision using BigDecimal';
COMMENT ON COLUMN timestamp_cache_orders.quantity IS 'Quantity of products ordered';
COMMENT ON COLUMN timestamp_cache_orders.order_date IS 'Timestamp when order was placed';
COMMENT ON COLUMN timestamp_cache_orders.status IS 'Order status (COMPLETED, PENDING, CANCELLED)';
COMMENT ON COLUMN timestamp_cache_orders.active IS 'Whether the order is currently active';
