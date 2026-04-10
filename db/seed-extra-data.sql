-- Re-runnable SQL seed for extra demo restaurants and dishes
-- Usage:
-- mysql -u root -p'your_password' -D food_ordering_system < db/seed-extra-data.sql

-- Restaurant: BBQ Junction
INSERT INTO users (email, password, full_name, role, restaurant_name, phone_number, is_active, created_at, updated_at)
SELECT
    'bbq.junction@foodapp.com',
    u.password,
    'Rohit Malhotra',
    'RESTAURANT',
    'BBQ Junction',
    '9999999912',
    1,
    NOW(),
    NOW()
FROM users u
WHERE u.email = 'restaurant@foodapp.com'
  AND NOT EXISTS (SELECT 1 FROM users WHERE email = 'bbq.junction@foodapp.com')
LIMIT 1;

-- Restaurant: Royal Biryani House
INSERT INTO users (email, password, full_name, role, restaurant_name, phone_number, is_active, created_at, updated_at)
SELECT
    'royal.biryani@foodapp.com',
    u.password,
    'Ayesha Khan',
    'RESTAURANT',
    'Royal Biryani House',
    '9999999913',
    1,
    NOW(),
    NOW()
FROM users u
WHERE u.email = 'restaurant@foodapp.com'
  AND NOT EXISTS (SELECT 1 FROM users WHERE email = 'royal.biryani@foodapp.com')
LIMIT 1;

-- Restaurant: Taco Fiesta
INSERT INTO users (email, password, full_name, role, restaurant_name, phone_number, is_active, created_at, updated_at)
SELECT
    'taco.fiesta@foodapp.com',
    u.password,
    'Carlos Dsouza',
    'RESTAURANT',
    'Taco Fiesta',
    '9999999914',
    1,
    NOW(),
    NOW()
FROM users u
WHERE u.email = 'restaurant@foodapp.com'
  AND NOT EXISTS (SELECT 1 FROM users WHERE email = 'taco.fiesta@foodapp.com')
LIMIT 1;

-- BBQ Junction menu
INSERT INTO menu_items (restaurant_id, name, description, price, category, available, image_url, created_at, updated_at)
SELECT u.id, 'Smoked BBQ Chicken', 'Char-grilled chicken with smoky glaze', 349.00, 'BBQ', 1, 'https://images.unsplash.com/photo-1529193591184-b1d58069ecdd?auto=format&fit=crop&w=800&q=80', NOW(), NOW()
FROM users u
WHERE u.email = 'bbq.junction@foodapp.com'
  AND NOT EXISTS (SELECT 1 FROM menu_items m WHERE m.restaurant_id = u.id AND m.name = 'Smoked BBQ Chicken');

INSERT INTO menu_items (restaurant_id, name, description, price, category, available, image_url, created_at, updated_at)
SELECT u.id, 'BBQ Paneer Skewers', 'Paneer cubes grilled with spices', 269.00, 'BBQ', 1, 'https://images.unsplash.com/photo-1694845287479-6a31e8e0d608?auto=format&fit=crop&w=800&q=80', NOW(), NOW()
FROM users u
WHERE u.email = 'bbq.junction@foodapp.com'
  AND NOT EXISTS (SELECT 1 FROM menu_items m WHERE m.restaurant_id = u.id AND m.name = 'BBQ Paneer Skewers');

INSERT INTO menu_items (restaurant_id, name, description, price, category, available, image_url, created_at, updated_at)
SELECT u.id, 'Grilled Corn Ribs', 'Sweet corn ribs with paprika butter', 179.00, 'Sides', 1, 'https://images.unsplash.com/photo-1551754655-9292b87a6d96?auto=format&fit=crop&w=800&q=80', NOW(), NOW()
FROM users u
WHERE u.email = 'bbq.junction@foodapp.com'
  AND NOT EXISTS (SELECT 1 FROM menu_items m WHERE m.restaurant_id = u.id AND m.name = 'Grilled Corn Ribs');

-- Royal Biryani House menu
INSERT INTO menu_items (restaurant_id, name, description, price, category, available, image_url, created_at, updated_at)
SELECT u.id, 'Hyderabadi Chicken Biryani', 'Long-grain basmati and spicy chicken', 329.00, 'Biryani', 1, 'https://images.unsplash.com/photo-1589302168068-964664d93dc0?auto=format&fit=crop&w=800&q=80', NOW(), NOW()
FROM users u
WHERE u.email = 'royal.biryani@foodapp.com'
  AND NOT EXISTS (SELECT 1 FROM menu_items m WHERE m.restaurant_id = u.id AND m.name = 'Hyderabadi Chicken Biryani');

INSERT INTO menu_items (restaurant_id, name, description, price, category, available, image_url, created_at, updated_at)
SELECT u.id, 'Mutton Biryani', 'Slow-cooked mutton biryani', 389.00, 'Biryani', 1, 'https://images.unsplash.com/photo-1642821373181-696a54906e93?auto=format&fit=crop&w=800&q=80', NOW(), NOW()
FROM users u
WHERE u.email = 'royal.biryani@foodapp.com'
  AND NOT EXISTS (SELECT 1 FROM menu_items m WHERE m.restaurant_id = u.id AND m.name = 'Mutton Biryani');

INSERT INTO menu_items (restaurant_id, name, description, price, category, available, image_url, created_at, updated_at)
SELECT u.id, 'Double Ka Meetha', 'Traditional hyderabadi dessert', 149.00, 'Desserts', 1, 'https://images.unsplash.com/photo-1578985545062-69928b1d9587?auto=format&fit=crop&w=800&q=80', NOW(), NOW()
FROM users u
WHERE u.email = 'royal.biryani@foodapp.com'
  AND NOT EXISTS (SELECT 1 FROM menu_items m WHERE m.restaurant_id = u.id AND m.name = 'Double Ka Meetha');

-- Taco Fiesta menu
INSERT INTO menu_items (restaurant_id, name, description, price, category, available, image_url, created_at, updated_at)
SELECT u.id, 'Veggie Taco Trio', 'Three tacos with salsa and crema', 249.00, 'Mexican', 1, 'https://images.unsplash.com/photo-1565299585323-38174cbca7b3?auto=format&fit=crop&w=800&q=80', NOW(), NOW()
FROM users u
WHERE u.email = 'taco.fiesta@foodapp.com'
  AND NOT EXISTS (SELECT 1 FROM menu_items m WHERE m.restaurant_id = u.id AND m.name = 'Veggie Taco Trio');

INSERT INTO menu_items (restaurant_id, name, description, price, category, available, image_url, created_at, updated_at)
SELECT u.id, 'Chicken Quesadilla', 'Cheesy grilled tortilla with chicken', 289.00, 'Mexican', 1, 'https://images.unsplash.com/photo-1618040996337-56904b7850b9?auto=format&fit=crop&w=800&q=80', NOW(), NOW()
FROM users u
WHERE u.email = 'taco.fiesta@foodapp.com'
  AND NOT EXISTS (SELECT 1 FROM menu_items m WHERE m.restaurant_id = u.id AND m.name = 'Chicken Quesadilla');

INSERT INTO menu_items (restaurant_id, name, description, price, category, available, image_url, created_at, updated_at)
SELECT u.id, 'Churros with Chocolate', 'Cinnamon churros and chocolate dip', 169.00, 'Desserts', 1, 'https://images.unsplash.com/photo-1624353365286-3f8d62e63e9d?auto=format&fit=crop&w=800&q=80', NOW(), NOW()
FROM users u
WHERE u.email = 'taco.fiesta@foodapp.com'
  AND NOT EXISTS (SELECT 1 FROM menu_items m WHERE m.restaurant_id = u.id AND m.name = 'Churros with Chocolate');
