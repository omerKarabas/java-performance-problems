-- ================================================================
-- V7: Create Null Validation Cache Problem Tables
-- ================================================================
-- This migration creates tables to demonstrate cache key null validation issues
-- where null values in cache key generation can cause problems

-- Create null_validation_cache table
CREATE TABLE null_validation_cache (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(100) NULL,              -- Can be null for guest users
    product_id VARCHAR(100) NOT NULL,       -- Should not be null
    category_id VARCHAR(100) NULL,          -- Can be null for general category
    description TEXT NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create indexes for better query performance
CREATE INDEX idx_null_validation_user_id ON null_validation_cache(user_id);
CREATE INDEX idx_null_validation_product_id ON null_validation_cache(product_id);
CREATE INDEX idx_null_validation_category_id ON null_validation_cache(category_id);
CREATE INDEX idx_null_validation_active ON null_validation_cache(active);
CREATE INDEX idx_null_validation_composite ON null_validation_cache(user_id, product_id, category_id, active);

-- Insert test data to demonstrate null validation scenarios
INSERT INTO null_validation_cache (user_id, product_id, category_id, description) VALUES
    -- Scenario 1: Guest users (null user_id)
    (NULL, 'laptop-001', 'electronics', 'High-performance gaming laptop for guest user browsing'),
    (NULL, 'mouse-001', 'electronics', 'Wireless gaming mouse for anonymous visitor'),
    (NULL, 'keyboard-001', NULL, 'Mechanical keyboard with no specific category'),
    
    -- Scenario 2: Registered users with valid user_id
    ('user123', 'laptop-002', 'electronics', 'Business laptop for registered user'),
    ('user456', 'phone-001', 'electronics', 'Smartphone for premium user account'),
    ('user789', 'tablet-001', NULL, 'Tablet with general category for user'),
    
    -- Scenario 3: Mixed scenarios with some null values
    ('user999', 'headset-001', 'audio', 'Gaming headset for registered user'),
    (NULL, 'monitor-001', 'displays', 'Ultra-wide monitor for guest browsing'),
    ('user111', 'camera-001', NULL, 'Digital camera with no category specified'),
    (NULL, 'speaker-001', NULL, 'Bluetooth speaker - guest user, no category'),
    
    -- Scenario 4: Product variations for cache key testing
    ('user222', 'laptop-gaming-001', 'gaming', 'Gaming laptop with specific category'),
    (NULL, 'laptop-business-001', 'business', 'Business laptop for guest user'),
    ('user333', 'laptop-ultrabook-001', NULL, 'Ultrabook without category classification'),
    
    -- Scenario 5: Additional test cases for null combinations
    (NULL, 'accessory-001', 'accessories', 'Computer accessory for anonymous user'),
    ('user444', 'accessory-002', 'accessories', 'Premium accessory for registered user'),
    (NULL, 'software-001', NULL, 'Software product - guest user, no category'),
    ('user555', 'software-002', NULL, 'Software license for registered user'),
    
    -- Scenario 6: Edge cases for testing
    ('', 'empty-user-test', 'test', 'Test case with empty string user ID'),
    ('user666', '', 'test', 'Test case with empty string product ID'),
    ('user777', 'normal-product', '', 'Test case with empty string category');

-- Add some comments for documentation
COMMENT ON TABLE null_validation_cache IS 'Table for demonstrating cache key null validation problems and solutions';
COMMENT ON COLUMN null_validation_cache.user_id IS 'User identifier - can be null for guest/anonymous users';
COMMENT ON COLUMN null_validation_cache.product_id IS 'Product identifier - should not be null in real scenarios';
COMMENT ON COLUMN null_validation_cache.category_id IS 'Category identifier - can be null for general/uncategorized products';
COMMENT ON COLUMN null_validation_cache.description IS 'Product description for context';
COMMENT ON COLUMN null_validation_cache.active IS 'Flag to enable/disable records for testing';

-- Insert statistics for monitoring
INSERT INTO null_validation_cache (user_id, product_id, category_id, description) VALUES
    (NULL, 'stats-null-users', 'stats', 'Counter for null user scenarios'),
    ('stats-user', 'stats-registered-users', 'stats', 'Counter for registered user scenarios'),
    (NULL, 'stats-null-categories', NULL, 'Counter for null category scenarios'),
    ('stats-user', 'stats-mixed-scenarios', NULL, 'Counter for mixed null/non-null scenarios');
