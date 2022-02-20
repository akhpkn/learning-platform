create table course_assignments(
    employee_id uuid not null references employees(id),
    course_id bigint not null references courses(id),
    initiator_id uuid not null references employees(id),
    primary key (employee_id, course_id)
);
