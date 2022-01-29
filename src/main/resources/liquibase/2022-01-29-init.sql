create table courses(
    id bigserial primary key,
    title varchar not null,
    description varchar
);

create table lessons(
    id bigserial primary key,
    title varchar not null,
    course_id bigint references courses(id) not null
);
