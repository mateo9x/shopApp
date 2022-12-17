ALTER TABLE items ALTER COLUMN price TYPE double precision USING price::double precision;

insert into items (brand, model, price, items_category_id, amount_available, create_date, seller_id, description, photos) values
('Apple', 'Iphone X', '1500.99', 1, 10, '2021-10-24 14:54', 1, 'Precyzja wykonania', 'iphone-x.png'),
('Apple', 'Iphone 12', '3200.99', 1, 12, '2021-10-23 13:54', 1, 'Prostota i elegancja w jednym', 'iphone-12.jpg'),
('Apple', 'Iphone 8', '1200.99', 1, 5, '2021-10-20 13:54', 1, 'Co tu dodać...', 'iphone-8.jpg'),
('Apple', 'Iphone 12 PRO', '4200.99', 1, 6, '2021-10-23 12:33', 1, 'IOS rulez', 'iphone-12-pro-blue-hero.jpg;12ProGold.jpg'),
('Samsung', 'J3', '600.99', 1, 8, '2021-10-22 14:54', 3, 'Zwykły, ale działa', 'j3.jpg'),
('ULTER', 'X-Pipe', '2400', 8, 9, '2021-10-24 14:54', 2, 'Stop aluminiowy', 'x-pipe.jpg'),
('ULTER', 'CatBack', '800', 8, 11, '2021-10-24 14:54', 2, 'Będzie głośno', 'catback.jpg');
