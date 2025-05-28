-- Insert 10 clients
INSERT INTO client (name) VALUES
('John Smith'),
('Maria Garcia'),
('Chen Wei'),
('Ahmed Hassan'),
('Sarah Johnson'),
('Dmitri Shevchenko'),
('Isabella Rodriguez'),
('Kenji Tanaka'),
('Emma Thompson'),
('Raj Patel');

-- Insert 5 planets
INSERT INTO planet (id, name) VALUES
('EARTH', 'Earth'),
('MARS', 'Mars'),
('VENUS', 'Venus'),
('JUPITER1', 'Jupiter Station Alpha'),
('MOON', 'Luna Base');

-- Insert 10 tickets
INSERT INTO ticket (client_id, from_planet_id, to_planet_id, created_at) VALUES
(1, 'EARTH', 'MARS', '2025-05-15 10:30:00'),
(2, 'MARS', 'VENUS', '2025-05-16 14:45:00'),
(3, 'EARTH', 'MOON', '2025-05-17 09:15:00'),
(4, 'VENUS', 'EARTH', '2025-05-18 16:20:00'),
(5, 'MARS', 'JUPITER1', '2025-05-19 11:00:00'),
(6, 'MOON', 'EARTH', '2025-05-20 13:30:00'),
(7, 'EARTH', 'JUPITER1', '2025-05-21 08:45:00'),
(8, 'JUPITER1', 'MARS', '2025-05-22 15:10:00'),
(9, 'VENUS', 'MOON', '2025-05-23 12:25:00'),
(10, 'EARTH', 'VENUS', '2025-05-24 17:50:00');