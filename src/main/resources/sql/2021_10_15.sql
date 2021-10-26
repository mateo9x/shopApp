ALTER TABLE db.items ALTER COLUMN price TYPE double precision USING price::double precision;

insert into items (brand, model, price, items_category_id, sold, create_date, seller_id, description) values 
('Apple', 'Iphone X', '1500.99', 5, false, '2021-10-24 14:54', 1, 'Precyzja wykonania'),
('Apple', 'Iphone 12', '3200.99', 5, false, '2021-10-23 13:54', 1, 'Prostota i elegancja w jednym'),
('Apple', 'Iphone 8', '1200.99', 5, false, '2021-10-20 13:54', 1, 'Co tu dodać...'),
('Apple', 'Iphone 12 PRO', '4200.99', 5, false, '2021-10-23 12:33', 1, 'IOS rulez'),
('Samsung', 'J3', '600.99', 3, false, '2021-10-22 14:54', 3, 'Zwykły, ale działa'),
('ULTER', 'X-Pipe', '2400', 8, false, '2021-10-24 14:54', 2, 'Stop aluminiowy'),
('ULTER', 'CatBack', '800', 8, false, '2021-10-24 14:54', 2, 'Będzie głośno');