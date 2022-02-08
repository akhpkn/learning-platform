create table courses(
    id bigserial primary key,
    title varchar not null,
    description varchar
);

create table lessons(
    id bigserial primary key,
    title varchar not null,
    video_id bigint references video_id(id),
    course_id bigint references courses(id) not null
);

create table videos(
    id bigserial primary key,
    type varchar not null,
    url varchar not null
);