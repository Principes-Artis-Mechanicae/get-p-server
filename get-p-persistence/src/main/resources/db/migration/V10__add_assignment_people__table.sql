create table assignment_people
(
    assignment_people_id bigint auto_increment,
    project_id         bigint       null,
    people_id          bigint       null,
    created_at         timestamp    null,
    updated_at         timestamp    null,
    primary key (assignment_people_id)
);
