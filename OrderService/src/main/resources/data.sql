-- Clear existing data
DELETE FROM order_item;
DELETE FROM orders;

-- Reset sequences first
ALTER SEQUENCE IF EXISTS orders_id_seq RESTART WITH 1;
ALTER SEQUENCE IF EXISTS order_item_id_seq RESTART WITH 1;

-- Insert orders with explicit IDs
INSERT INTO orders (id, order_status, price) VALUES (1, 'ORDER_ADDED', 1200.00);
INSERT INTO orders (id, order_status, price) VALUES (2, 'CONFIRMED', 800.00);
INSERT INTO orders (id, order_status, price) VALUES (3, 'DELIVERED', 150.00);
INSERT INTO orders (id, order_status, price) VALUES (4, 'ORDER_ADDED', 300.00);
INSERT INTO orders (id, order_status, price) VALUES (5, 'CONFIRMED', 50.00);

-- Insert order items with explicit IDs
INSERT INTO order_item (id, product_id, quantity, order_id) VALUES (1, 101, 1, 1);
INSERT INTO order_item (id, product_id, quantity, order_id) VALUES (2, 102, 2, 2);
INSERT INTO order_item (id, product_id, quantity, order_id) VALUES (3, 103, 1, 3);
INSERT INTO order_item (id, product_id, quantity, order_id) VALUES (4, 104, 3, 4);
INSERT INTO order_item (id, product_id, quantity, order_id) VALUES (5, 105, 5, 5);

-- Update sequences to next available value
SELECT setval('orders_id_seq', 6, false);
SELECT setval('order_item_id_seq', 6, false);