--liquibase formatted sql
--changeset mateo9x:1
create table roles (
    id serial not null,
    name varchar(100) not null,
    CONSTRAINT roles_pk PRIMARY KEY(id)
);

create table users_roles (
    user_id bigint not null,
    roles_id bigint not null,
    CONSTRAINT users_roles_pk PRIMARY KEY(user_id),
    FOREIGN KEY (roles_id) REFERENCES roles (id)
);

create table sqlversion (
    id serial not null,
    version varchar(100) not null,
    CONSTRAINT sqlversion_pk PRIMARY KEY(id)
);

GRANT all on users_roles to postgres;
GRANT all on roles to postgres;
GRANT all on sqlversion to postgres;

insert into sqlversion values (1, '2021_10_10');