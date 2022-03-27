create table scores(
    id bigserial primary key,
    author_id uuid not null,
    course_id bigint references courses(id) not null,
    score bigint not null
);