ALTER TABLE items ALTER COLUMN price TYPE double precision USING price::double precision;

insert into items (brand, model, price, items_category_id, amount_available, create_date, seller_id, description, photos) values
('Apple', 'Iphone X', '1500.99', 1, 10, '2021-10-24 14:54', 1, 'Precyzja wykonania', 'https://d.mtechimg.com/18999/apple-iphone-x.webp'),
('Apple', 'Iphone 12', '3200.99', 1, 12, '2021-10-23 13:54', 1, 'Prostota i elegancja w jednym', 'https://caseroom.pl/735-large_default/apple-iphone-12_etui-silikonowe.jpg'),
('Apple', 'Iphone 8', '1200.99', 1, 5, '2021-10-20 13:54', 1, 'Co tu dodać...', 'https://files.refurbed.com/pi/apple-iphone-8-1562312834.jpg'),
('Apple', 'Iphone 12 PRO', '4200.99', 1, 6, '2021-10-23 12:33', 1, 'IOS rulez', 'https://cdn.dxomark.com/wp-content/uploads/medias/post-61263/iphone-12-pro-blue-hero.jpg;https://luxtrade.pl/img/cms/12ProGold.jpg'),
('Samsung', 'J3', '600.99', 1, 8, '2021-10-22 14:54', 3, 'Zwykły, ale działa', 'https://skuptelefonow.pl/wp-content/uploads/2020/02/Samsung_Galaxy_J_59a978459731c.jpg'),
('ULTER', 'X-Pipe', '2400', 8, 9, '2021-10-24 14:54', 2, 'Stop aluminiowy', 'https://grubygarage.com.pl/storage/products/30402/_original_Pipe-70mm_[284881]_0c20274c9e34.jpg'),
('ULTER', 'CatBack', '800', 8, 11, '2021-10-24 14:54', 2, 'Będzie głośno', 'https://www.rpmotorsport.pl/c/2543-category_default/uk%C5%82ady-wydechowe-cat-back.jpg');
