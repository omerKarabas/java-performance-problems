-- Migration for Mutable Cache Key Problem demonstration
-- Creates table and sample data for mutable key scenarios

-- Create mutable_key_cache table
CREATE TABLE mutable_key_cache (
    id BIGSERIAL PRIMARY KEY,
    category VARCHAR(100) NOT NULL,
    tag VARCHAR(100) NOT NULL,
    description VARCHAR(500) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better query performance
CREATE INDEX idx_mutable_key_cache_category ON mutable_key_cache(category);
CREATE INDEX idx_mutable_key_cache_tag ON mutable_key_cache(tag);
CREATE INDEX idx_mutable_key_cache_active ON mutable_key_cache(active);
CREATE INDEX idx_mutable_key_cache_category_tag ON mutable_key_cache(category, tag);

-- Insert sample data for testing mutable key scenarios
INSERT INTO mutable_key_cache (category, tag, description) VALUES
-- Electronics category
('electronics', 'laptop', 'High-performance gaming laptop with RTX 4080'),
('electronics', 'smartphone', 'Latest flagship smartphone with AI features'),
('electronics', 'tablet', 'Professional tablet for digital artists'),
('electronics', 'headphones', 'Noise-cancelling wireless headphones'),
('electronics', 'smartwatch', 'Fitness tracking smartwatch with GPS'),

-- Clothing category
('clothing', 'shirt', 'Premium cotton casual shirt'),
('clothing', 'jeans', 'Classic blue denim jeans'),
('clothing', 'jacket', 'Waterproof outdoor hiking jacket'),
('clothing', 'shoes', 'Running shoes with advanced cushioning'),
('clothing', 'sweater', 'Wool blend winter sweater'),

-- Books category
('books', 'fiction', 'Award-winning science fiction novel'),
('books', 'programming', 'Advanced Java programming guide'),
('books', 'cooking', 'Mediterranean cuisine cookbook'),
('books', 'history', 'World War II historical analysis'),
('books', 'science', 'Quantum physics for beginners'),

-- Sports category
('sports', 'football', 'Professional grade football equipment'),
('sports', 'basketball', 'Official size basketball with grip technology'),
('sports', 'tennis', 'Carbon fiber tennis racket'),
('sports', 'swimming', 'Competitive swimming goggles'),
('sports', 'cycling', 'Mountain bike helmet with ventilation'),

-- Home category
('home', 'furniture', 'Ergonomic office chair with lumbar support'),
('home', 'kitchen', 'Stainless steel cookware set'),
('home', 'decor', 'Modern abstract wall art'),
('home', 'lighting', 'Smart LED bulbs with color changing'),
('home', 'storage', 'Modular storage solution for small spaces'),

-- Mixed category combinations for testing collision scenarios
('toys', 'educational', 'STEM learning kit for children'),
('toys', 'puzzle', '1000-piece landscape puzzle'),
('automotive', 'accessories', 'Premium car seat covers'),
('automotive', 'tools', 'Professional auto repair toolkit'),
('garden', 'plants', 'Organic herb growing starter kit'),
('garden', 'tools', 'Ergonomic gardening tool set');

-- Add some duplicate category/tag combinations with different descriptions
-- to test caching behavior with multiple results
INSERT INTO mutable_key_cache (category, tag, description) VALUES
('electronics', 'laptop', 'Budget-friendly laptop for students'),
('electronics', 'laptop', 'Ultra-thin business laptop with long battery'),
('clothing', 'shirt', 'Formal dress shirt for business occasions'),
('clothing', 'shirt', 'Casual polo shirt for weekend wear'),
('books', 'programming', 'JavaScript fundamentals and frameworks'),
('books', 'programming', 'Machine learning with Python guide');

-- Comments explaining the test scenarios
/*
This migration creates test data for demonstrating mutable cache key problems:

1. MUTABLE KEY SCENARIO:
   - categories: ["electronics", "clothing"]
   - tags: ["laptop", "shirt"]
   - Original List: [electronics, clothing, laptop, shirt]
   - After modification: [electronics, clothing, laptop, shirt, new-category, new-tag]
   - Result: Cache key changes, causing cache miss

2. SAFE STRING KEY SCENARIO:
   - Same input data
   - String key: "electronics:clothing:laptop:shirt"
   - After List modification: Key remains the same
   - Result: Cache hit, consistent behavior

3. HASH-BASED KEY SCENARIO:
   - Same input data
   - Structured key: "search:cat:electronics:clothing:tag:laptop:shirt"
   - After List modification: Key remains the same
   - Result: Cache hit with clear structure

Test cases to verify:
- Single category queries: category = 'electronics'
- Single tag queries: tag = 'laptop'
- Multiple categories: ['electronics', 'books']
- Multiple tags: ['laptop', 'programming']
- Mixed queries: categories=['electronics'] AND tags=['laptop']
- Empty/null handling: categories=null, tags=['laptop']

Expected behavior:
- Mutable keys: Inconsistent cache behavior
- String keys: Stable caching with proper hits
- Hash keys: Structured, debuggable, consistent caching
*/
