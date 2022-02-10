create table videos(
    id bigint primary key,
    type varchar not null,
    url varchar not null
);

alter table lessons add column video_id bigint references videos(id);