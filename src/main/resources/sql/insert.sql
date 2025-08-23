INSERT INTO products (product_id, name, description, price, old_price, is_available_at_web, quantity, created_at,
                      created_by, updated_at, updated_by)
VALUES (1, 'Ristretto Bianco', 'a', 5.0, 6.0, FALSE, 40, CURRENT_TIMESTAMP, 'admin', NULL, NULL);
INSERT INTO products (product_id, name, description, price, old_price, is_available_at_web, quantity, created_at,
                      created_by, updated_at, updated_by)
VALUES (2, 'French toast WITH SUGAR', 'a', 5.0, 6.0, FALSE, 50, CURRENT_TIMESTAMP, 'admin', NULL, NULL);
INSERT INTO products (product_id, name, description, price, old_price, is_available_at_web, quantity, created_at,
                      created_by, updated_at, updated_by)
VALUES (3, 'AT HOME HOUSE BLEND', 'a', 5.0, 6.0, TRUE, 70, CURRENT_TIMESTAMP, 'admin', NULL, NULL);
INSERT INTO products (product_id, name, description, price, old_price, is_available_at_web, quantity, created_at,
                      created_by, updated_at, updated_by)
VALUES (4, 'AT HOME HOUSE classic', 'a', 5.0, 6.0, TRUE, 20, CURRENT_TIMESTAMP, 'admin', NULL, NULL);

INSERT INTO product_images (product_id, image_url, created_at, created_by, updated_at, updated_by)
VALUES (1, '/home/menu/Sections/Image1.png', CURRENT_TIMESTAMP, 'admin', NULL, NULL);
INSERT INTO product_images (product_id, image_url, created_at, created_by, updated_at, updated_by)
VALUES (2, '/home/menu/Sections/Image2.png', CURRENT_TIMESTAMP, 'admin', NULL, NULL);
INSERT INTO product_images (product_id, image_url, created_at, created_by, updated_at, updated_by)
VALUES (3, '/home/menu/Sections/Image3.png', CURRENT_TIMESTAMP, 'admin', NULL, NULL);
INSERT INTO product_images (product_id, image_url, created_at, created_by, updated_at, updated_by)
VALUES (4, '/home/menu/Sections/Image4.png', CURRENT_TIMESTAMP, 'admin', NULL, NULL);


INSERT INTO news (new_id, title, description, image, created_at, created_by, updated_at, updated_by)
VALUES (1, '', 'desc', '/news/Sections/Image1.png', '2022-08-19 00:00:00', 'admin', NULL, NULL),

       (2, 'Cold Brew Coffee, How to Drink Cold Coffee is More Enjoyable', 'desc', '/news/Sections/Image2.png',
        '2022-08-19 00:00:00', 'admin', NULL, NULL),

       (3, 'Meet Coffee Tonic, the Sensation of Drinking Coffee-Flavored Soda', 'desc', '/news/Sections/Image3.png',
        '2022-08-19 00:00:00', 'admin', NULL, NULL),

       (4, 'Workshop Coffee Sharing Session', 'desc', '/news/Sections/Image4.png', '2022-08-19 00:00:00', 'admin',
        NULL, NULL),

       (5, 'Workshop Coffee Brewing', 'desc', '/news/Sections/Image5.png', '2022-08-19 00:00:00', 'admin', NULL, NULL);


INSERT INTO event (event_id, name, image, start_date, duration, created_at, created_by, updated_at, updated_by)
VALUES (1, 'latter art workshop', '/home/event/card/Sections/Image1.png', '2023-02-20 00:00:00', '02:00:00',
        '2023-02-20 00:00:00', 'admin', NULL, NULL),

       (2, 'EXHIBITION COFFEE HARDWARE', '/home/event/card/Sections/Image2.png', '2023-03-20 00:00:00', '02:00:00',
        '2023-03-20 00:00:00', 'admin', NULL,
        NULL),

       (3, 'Factory visit', '/home/event/card/Sections/Image3.png', '2023-04-20 00:00:00', '02:00:00',
        '2023-04-20 00:00:00', 'admin', NULL, NULL);

INSERT INTO event (event_id, name, image, start_date, duration, created_by, updated_at, updated_by)
VALUES (4, 'Bezzera Latte Art Competition', '/event/Sections/Image1.png', '2023-02-20 00:00:00', '02:00:00', 'system',
        NULL, NULL),

       (5, 'SENSORY AND CUPPING CLASS', '/event/Sections/Image2.png', '2023-03-20 00:00:00', '02:00:00', 'system', NULL,
        NULL),

       (6, 'Public Cupping', '/event/Sections/Image3.png', '2023-02-20 00:00:00', '02:00:00', 'system', NULL, NULL),

       (7, 'Competitions and Showcases', '/event/Sections/Image4.png', '2023-03-20 00:00:00', '02:00:00', 'system',
        NULL, NULL),

       (8, 'Art and Coffee Festival', '/event/Sections/Image5.png', '2023-03-20 00:00:00', '02:00:00', 'system', NULL,
        NULL);
