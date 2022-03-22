create table employee_progress(
  id uuid primary key,
  employee_id uuid references employees(id),
  courses_completed bigint not null default 0
);

create table lesson_progress(
    id uuid primary key,
    progress_id uuid references employee_progress(id),
    lesson_id bigint references lessons(id)
);
