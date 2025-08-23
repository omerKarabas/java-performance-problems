-- Migration for Consistent Ordering Cache Key Problem demonstration
-- Creates table and sample data for consistent ordering scenarios

-- Create search_parameter_cache table
CREATE TABLE search_parameter_cache (
    id BIGSERIAL PRIMARY KEY,
    category VARCHAR(100) NOT NULL,
    brand VARCHAR(100) NOT NULL,
    product_name VARCHAR(200) NOT NULL,
    min_price DECIMAL(10,2) NOT NULL,
    max_price DECIMAL(10,2) NOT NULL,
    region VARCHAR(100) NOT NULL,
    rating INTEGER NOT NULL,
    active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better query performance
CREATE INDEX idx_search_parameter_cache_category ON search_parameter_cache(category);
CREATE INDEX idx_search_parameter_cache_brand ON search_parameter_cache(brand);
CREATE INDEX idx_search_parameter_cache_region ON search_parameter_cache(region);
CREATE INDEX idx_search_parameter_cache_rating ON search_parameter_cache(rating);
CREATE INDEX idx_search_parameter_cache_price_range ON search_parameter_cache(min_price, max_price);
CREATE INDEX idx_search_parameter_cache_active ON search_parameter_cache(active);
CREATE INDEX idx_search_parameter_cache_category_brand ON search_parameter_cache(category, brand);
CREATE INDEX idx_search_parameter_cache_category_region ON search_parameter_cache(category, region);

-- Insert sample data for testing consistent ordering scenarios
INSERT INTO search_parameter_cache (category, brand, product_name, min_price, max_price, region, rating) VALUES
-- Electronics category with different brands and regions
('electronics', 'samsung', 'Galaxy S24 Ultra Smartphone', 999.99, 1299.99, 'europe', 5),
('electronics', 'apple', 'iPhone 15 Pro Max', 1099.99, 1399.99, 'europe', 5),
('electronics', 'samsung', 'Galaxy Tab S9 Tablet', 699.99, 899.99, 'asia', 4),
('electronics', 'apple', 'iPad Pro 12.9', 799.99, 1099.99, 'asia', 5),
('electronics', 'sony', 'WH-1000XM5 Headphones', 349.99, 399.99, 'europe', 5),
('electronics', 'bose', 'QuietComfort 45 Headphones', 329.99, 379.99, 'america', 4),
('electronics', 'dell', 'XPS 13 Laptop', 899.99, 1199.99, 'europe', 4),
('electronics', 'lenovo', 'ThinkPad X1 Carbon', 1299.99, 1599.99, 'america', 5),

-- Clothing category with different brands and regions
('clothing', 'nike', 'Air Max 270 Running Shoes', 129.99, 159.99, 'europe', 4),
('clothing', 'adidas', 'Ultraboost 22 Shoes', 179.99, 199.99, 'america', 5),
('clothing', 'zara', 'Premium Cotton Shirt', 49.99, 69.99, 'europe', 3),
('clothing', 'h&m', 'Classic Denim Jeans', 39.99, 59.99, 'asia', 4),
('clothing', 'levis', '501 Original Jeans', 79.99, 99.99, 'america', 4),
('clothing', 'tommy hilfiger', 'Polo Shirt Collection', 59.99, 79.99, 'europe', 4),
('clothing', 'calvin klein', 'Underwear Set', 29.99, 49.99, 'america', 4),
('clothing', 'ralph lauren', 'Oxford Shirt', 89.99, 119.99, 'europe', 5),

-- Books category with different publishers and regions
('books', 'penguin', 'The Great Gatsby', 9.99, 14.99, 'europe', 5),
('books', 'harper collins', 'To Kill a Mockingbird', 12.99, 17.99, 'america', 5),
('books', 'random house', '1984 by George Orwell', 10.99, 15.99, 'europe', 4),
('books', 'simon schuster', 'The Catcher in the Rye', 11.99, 16.99, 'america', 4),
('books', 'macmillan', 'Pride and Prejudice', 8.99, 13.99, 'europe', 5),
('books', 'houghton mifflin', 'The Hobbit', 13.99, 18.99, 'america', 5),
('books', 'faber faber', 'Lord of the Flies', 9.99, 14.99, 'europe', 4),
('books', 'vintage', 'Animal Farm', 7.99, 12.99, 'america', 4),

-- Sports category with different brands and regions
('sports', 'wilson', 'Professional Tennis Racket', 199.99, 249.99, 'europe', 5),
('sports', 'head', 'Graphene Tennis Racket', 179.99, 229.99, 'america', 4),
('sports', 'nike', 'Mercurial Vapor Football Boots', 249.99, 299.99, 'europe', 5),
('sports', 'adidas', 'Predator Edge Football Boots', 229.99, 279.99, 'america', 4),
('sports', 'spalding', 'Official NBA Basketball', 69.99, 89.99, 'america', 5),
('sports', 'molten', 'FIBA Official Basketball', 59.99, 79.99, 'asia', 4),
('sports', 'speedo', 'Competition Swimming Goggles', 39.99, 59.99, 'europe', 4),
('sports', 'arena', 'Professional Swim Cap', 19.99, 29.99, 'asia', 4),

-- Home category with different brands and regions
('home', 'ikea', 'Billy Bookcase', 79.99, 99.99, 'europe', 4),
('home', 'wayfair', 'Modern Coffee Table', 149.99, 199.99, 'america', 4),
('home', 'west elm', 'Mid-Century Chair', 299.99, 399.99, 'america', 5),
('home', 'made.com', 'Scandinavian Sofa', 599.99, 799.99, 'europe', 4),
('home', 'crate barrel', 'Kitchen Mixer', 199.99, 249.99, 'america', 4),
('home', 'john lewis', 'Bedding Set', 89.99, 119.99, 'europe', 4),
('home', 'pottery barn', 'Table Lamp', 129.99, 159.99, 'america', 5),
('home', 'habitat', 'Wall Clock', 49.99, 69.99, 'europe', 3),

-- Mixed category combinations for testing ordering scenarios
('toys', 'lego', 'Star Wars Millennium Falcon', 799.99, 899.99, 'europe', 5),
('toys', 'mattel', 'Hot Wheels Track Set', 49.99, 69.99, 'america', 4),
('automotive', 'bosch', 'Professional Tool Set', 199.99, 249.99, 'europe', 5),
('automotive', 'dewalt', 'Cordless Drill Kit', 299.99, 349.99, 'america', 4),
('garden', 'fiskars', 'Professional Pruning Shears', 39.99, 59.99, 'europe', 4),
('garden', 'black decker', 'Electric Hedge Trimmer', 89.99, 119.99, 'america', 4);

-- Add some duplicate category/brand combinations with different regions
-- to test caching behavior with multiple results
INSERT INTO search_parameter_cache (category, brand, product_name, min_price, max_price, region, rating) VALUES
('electronics', 'samsung', 'Galaxy A54 Smartphone', 399.99, 499.99, 'europe', 4),
('electronics', 'samsung', 'Galaxy A54 Smartphone', 449.99, 549.99, 'america', 4),
('clothing', 'nike', 'Air Force 1 Sneakers', 99.99, 119.99, 'europe', 5),
('clothing', 'nike', 'Air Force 1 Sneakers', 109.99, 129.99, 'america', 5),
('books', 'penguin', 'The Great Gatsby', 12.99, 17.99, 'america', 5),
('books', 'penguin', 'The Great Gatsby', 11.99, 16.99, 'asia', 4);

-- Comments explaining the test scenarios
/*
This migration creates test data for demonstrating consistent ordering cache key problems:

1. CONSISTENT ORDERING PROBLEM SCENARIO:
   - Parameters: ["electronics", "samsung", "europe"]
   - HashSet order: Not guaranteed
   - Cache key might be: "electronicssamsungeurope" or "samsungelectronicseurope"
   - Result: Inconsistent cache keys for same parameters

2. SORTED SOLUTION SCENARIO:
   - Same parameters: ["electronics", "samsung", "europe"]
   - Sorted order: ["electronics", "europe", "samsung"]
   - Cache key: "electronics_europe_samsung" (always consistent)
   - Result: Same input always generates same cache key

3. TREESET SOLUTION SCENARIO:
   - Same parameters: ["electronics", "samsung", "europe"]
   - Natural ordering: ["electronics", "europe", "samsung"]
   - Cache key: "electronics_europe_samsung" (automatically consistent)
   - Result: Automatic ordering ensures consistency

Test cases to verify:
- Single category queries: category = 'electronics'
- Single brand queries: brand = 'samsung'
- Single region queries: region = 'europe'
- Multiple categories: ['electronics', 'clothing']
- Multiple brands: ['samsung', 'apple']
- Multiple regions: ['europe', 'america']
- Mixed queries: category='electronics' AND brand='samsung' AND region='europe'
- Price range queries: minPrice=100 AND maxPrice=500
- Rating queries: minRating=4
- Empty/null handling: category=null, brand='samsung'

Expected behavior:
- Problem endpoint: Inconsistent cache keys, poor cache performance
- Sorted solution: Consistent cache keys, proper cache hits
- TreeSet solution: Automatic consistent ordering, optimal performance

Cache key examples:
- Problem: "electronicssamsungeurope" vs "samsungelectronicseurope" (DIFFERENT!)
- Solution 1: "electronics_europe_samsung" (ALWAYS SAME)
- Solution 2: "electronics_europe_samsung" (ALWAYS SAME)
*/
