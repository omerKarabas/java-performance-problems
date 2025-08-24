-- Migration for Case Sensitivity Cache Key Problem Demo
-- This script creates tables for demonstrating cache key case sensitivity issues

-- Create case_sensitivity_user table for testing case sensitivity problems
CREATE TABLE case_sensitivity_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    region VARCHAR(100) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create index for case-sensitive queries (demonstrates the problem)
CREATE INDEX idx_case_sensitive_search ON case_sensitivity_user(user_name, category, region);

-- Create index for case-insensitive queries (part of the solution)
-- Note: H2 database compatible syntax
CREATE INDEX idx_case_insensitive_search ON case_sensitivity_user(user_name, category, region);

-- Insert sample data with different case variations to demonstrate the problem
INSERT INTO case_sensitivity_user (user_name, category, description, region) VALUES
-- Same logical user with different cases
('John', 'Electronics', 'Sample user for case sensitivity testing: John in US region', 'US'),
('john', 'electronics', 'Sample user for case sensitivity testing: john in us region', 'us'),
('JOHN', 'ELECTRONICS', 'Sample user for case sensitivity testing: JOHN in US region', 'US'),
('John', 'electronics', 'Sample user for case sensitivity testing: John in US region', 'US'),

-- Different users to show variety
('Alice', 'Books', 'Sample user for case sensitivity testing: Alice in UK region', 'UK'),
('alice', 'books', 'Sample user for case sensitivity testing: alice in uk region', 'uk'),
('ALICE', 'BOOKS', 'Sample user for case sensitivity testing: ALICE in UK region', 'UK'),

('Bob', 'Gaming', 'Sample user for case sensitivity testing: Bob in CA region', 'CA'),
('bob', 'gaming', 'Sample user for case sensitivity testing: bob in ca region', 'ca'),
('BOB', 'GAMING', 'Sample user for case sensitivity testing: BOB in CA region', 'CA'),

-- Edge cases with mixed case
('Sarah', 'Sports', 'Sample user for case sensitivity testing: Sarah in DE region', 'DE'),
('sArAh', 'sPoRtS', 'Sample user for case sensitivity testing: sArAh in dE region', 'dE'),
('SARAH', 'sports', 'Sample user for case sensitivity testing: SARAH in DE region', 'DE'),

-- More complex cases with special characters and spaces
('Mike Johnson', 'Home & Garden', 'Sample user for case sensitivity testing: Mike Johnson in FR region', 'FR'),
('mike johnson', 'home & garden', 'Sample user for case sensitivity testing: mike johnson in fr region', 'fr'),
('MIKE JOHNSON', 'HOME & GARDEN', 'Sample user for case sensitivity testing: MIKE JOHNSON in FR region', 'FR'),

-- Cases with leading/trailing spaces (trimming test cases)
(' Emma ', ' Fashion ', 'Sample user for case sensitivity testing:  Emma  in IT region', ' IT '),
('emma', 'fashion', 'Sample user for case sensitivity testing: emma in it region', 'it'),
('EMMA', 'FASHION', 'Sample user for case sensitivity testing: EMMA in IT region', 'IT');

-- Add some statistics tracking comments
-- These records demonstrate:
-- 1. Case sensitivity problems in cache keys
-- 2. How different cases create different cache entries
-- 3. Solutions using normalization and proper key generation
-- 4. Impact on cache hit ratios and performance

-- The case_sensitivity_user table is designed to:
-- - Store user data with various case combinations
-- - Demonstrate cache key generation problems
-- - Show solutions for consistent cache behavior
-- - Provide data for testing different normalization strategies

COMMIT;
