-- liquibase formatted sql

-- changeset action:alter_table_books_(add_description)
alter table book add column description varchar(20000);