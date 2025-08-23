-- Create tables for N+1 Query Disaster demonstration
-- This migration creates the necessary schema and test data to demonstrate
-- the N+1 query problem and its solutions
-- Tables are prefixed with 'nplusone_' to avoid conflicts with other modules

-- Create users table
CREATE TABLE nplusone_users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

-- Create orders table with foreign key relationship
-- This relationship will be used to demonstrate N+1 problem
CREATE TABLE nplusone_orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_number VARCHAR(255) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    nplusone_user_id BIGINT NOT NULL,
    FOREIGN KEY (nplusone_user_id) REFERENCES nplusone_users(id)
);

-- Insert test users for N+1 demonstration
-- 10 users will be created to show the problem clearly
INSERT INTO nplusone_users (username, email) VALUES
('user1', 'user1@example.com'),
('user2', 'user2@example.com'),
('user3', 'user3@example.com'),
('user4', 'user4@example.com'),
('user5', 'user5@example.com'),
('user6', 'user6@example.com'),
('user7', 'user7@example.com'),
('user8', 'user8@example.com'),
('user9', 'user9@example.com'),
('user10', 'user10@example.com');

-- Insert test orders to demonstrate N+1 problem
-- Each user has 3-6 orders to show the relationship loading issue
-- This will create the scenario: 1 query for users + N queries for orders
INSERT INTO nplusone_orders (order_number, amount, nplusone_user_id) VALUES
('ORD-1-1', 125.50, 1),
('ORD-1-2', 89.99, 1),
('ORD-1-3', 456.78, 1),
('ORD-2-1', 234.56, 2),
('ORD-2-2', 67.89, 2),
('ORD-2-3', 123.45, 2),
('ORD-2-4', 789.12, 2),
('ORD-3-1', 45.67, 3),
('ORD-3-2', 567.89, 3),
('ORD-4-1', 123.45, 4),
('ORD-4-2', 678.90, 4),
('ORD-4-3', 234.56, 4),
('ORD-4-4', 456.78, 4),
('ORD-4-5', 789.12, 4),
('ORD-5-1', 345.67, 5),
('ORD-5-2', 567.89, 5),
('ORD-5-3', 123.45, 5),
('ORD-6-1', 234.56, 6),
('ORD-6-2', 456.78, 6),
('ORD-6-3', 678.90, 6),
('ORD-6-4', 123.45, 6),
('ORD-7-1', 567.89, 7),
('ORD-7-2', 234.56, 7),
('ORD-7-3', 456.78, 7),
('ORD-8-1', 789.12, 8),
('ORD-8-2', 123.45, 8),
('ORD-8-3', 345.67, 8),
('ORD-8-4', 567.89, 8),
('ORD-8-5', 234.56, 8),
('ORD-8-6', 456.78, 8),
('ORD-9-1', 678.90, 9),
('ORD-9-2', 123.45, 9),
('ORD-9-3', 345.67, 9),
('ORD-10-1', 567.89, 10),
('ORD-10-2', 234.56, 10),
('ORD-10-3', 456.78, 10),
('ORD-10-4', 678.90, 10);
