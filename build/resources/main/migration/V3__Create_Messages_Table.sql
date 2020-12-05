create or replace table messages (
    system_id int auto_increment primary key,
    user_id int not null,
    user_id_target int not null,
    send_date TIMESTAMP not null default CURRENT_TIMESTAMP,
    text varchar(5000)
);

insert into messages (
    user_id,
    text,
    user_id_target
) values (
    1,
    'Hello, maaan))0) Some dope may be?)',
    2
);