--liquibase formatted sql
--changeset mateo9x:1
create table users (
    id bigint not null,
    username varchar(100) not null,
    firstname varchar(100) null,
    lastname varchar(100) null,
    mail varchar(100) not null,
    password varchar(100) not null,
    street varchar(100) null,
    street_number varchar(100) null,
    city varchar(100) null,
    roles_id int null,
    reset_password varchar(100) null,
    CONSTRAINT users_pk PRIMARY KEY(id),
    FOREIGN KEY (roles_id) REFERENCES roles (id)
);

ALTER TABLE users_roles ADD FOREIGN KEY (user_id) REFERENCES users (id);

create table items_category (
    id serial not null,
    name varchar(250) not null,
    CONSTRAINT items_category_pk PRIMARY KEY(id)
);

create table sellers (
    id serial not null,
    name varchar(250) not null,
    mail varchar(250) not null,
    CONSTRAINT sellers_pk PRIMARY KEY(id)
);

create table items (
    id serial not null,
    brand varchar(100) not null,
    model varchar(100) not null,
    price bigint null,
    items_category_id int null,
    amount_available integer null,
    create_date timestamp null,
    seller_id int null,
    description varchar null,
    photos varchar null,
    CONSTRAINT items_pk PRIMARY KEY(id),
    FOREIGN KEY (items_category_id) REFERENCES items_category (id),
    FOREIGN KEY (seller_id) REFERENCES sellers (id)
);

GRANT all on items to postgres;
GRANT all on users to postgres;
GRANT all on items_category to postgres;
GRANT all on sellers to postgres;
update sqlversion set version = '2021_10_11';
