create or replace table dialogs (
    system_id int auto_increment primary key,
    user_id int not null unique key,
    target_obj varchar(2000)
);

insert into dialogs (user_id, target_obj) values (1, '[]');