create or replace table usr (
    system_id int auto_increment primary key,
    name varchar(50) not null unique key,
    password varchar(50) not null
);

insert into usr (name, password) values ('maks', 'pass');