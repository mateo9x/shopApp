ALTER TABLE public.orders_address ADD postal_code varchar(6) NULL;

update sqlversion set version = '2022_12_01';
