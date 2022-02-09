create table employees(
    id uuid primary key,
    name varchar not null,
    surname varchar not null,
    email varchar unique not null,
    password varchar not null
);

create unique index employees_email_uidx on employees(email);

alter table courses add column author_id uuid references employees(id);
