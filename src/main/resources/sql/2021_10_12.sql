create table carts (
    id serial not null,
    user_id int null,
    item_id int null,
    amount_selected int not null,
    CONSTRAINT carts_pk PRIMARY KEY(id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (item_id) REFERENCES items (id)
);

create table orders_payment (
    id serial not null,
    type varchar(100) null,
    CONSTRAINT orders_payment_pk PRIMARY KEY(id)
);

create table orders_address (
    id serial not null,
    firstname varchar(100) null,
    lastname varchar(100) null,
    mail varchar(100) not null,
    street varchar(100) null,
    street_number varchar(100) null,
    city varchar(100) null,
    phone_number int null,
    CONSTRAINT orders_address_pk PRIMARY KEY(id)
);

create table orders (
    id serial not null,
    item_id int null,
    date timestamp null,
    amount_bought int not null,
    user_id int not null,
    order_address_id int null,
    order_payment_id int null,
    CONSTRAINT orders_pk PRIMARY KEY(id),
    FOREIGN KEY (item_id) REFERENCES items (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (order_address_id) REFERENCES orders_address (id),
    FOREIGN KEY (order_payment_id) REFERENCES orders_payment (id)
);

GRANT all on carts to postgres;
GRANT all on orders_address to postgres;
GRANT all on orders_payment to postgres;
GRANT all on orders to postgres;
update sqlversion set version = '2021_10_12';
