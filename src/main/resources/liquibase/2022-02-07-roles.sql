create table roles(
    name varchar primary key
);

insert into roles(name) values ('ROLE_EMPLOYEE');
insert into roles(name) values ('ROLE_ADMIN');

create table employee_roles(
    employee_id uuid not null references employees(id),
    role_name varchar not null references roles(name),
    primary key (employee_id, role_name)
);
