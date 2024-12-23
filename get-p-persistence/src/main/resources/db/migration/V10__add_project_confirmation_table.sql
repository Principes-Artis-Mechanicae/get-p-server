create table project_confirmation
(
    project_confirmation_id bigint auto_increment,
    project_id         bigint       null,
    people_id          bigint       null,
    created_at         timestamp    null,
    updated_at         timestamp    null,
    primary key (project_confirmation_id)
);
