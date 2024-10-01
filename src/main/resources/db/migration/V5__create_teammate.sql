create table team_project_application_teammate
(
    teammate_id bigint auto_increment not null,
    people_id bigint not null,
    status enum ('PENDING', 'APPROVED', 'REJECTED', 'EXPIRED') null,
    project_application_id bigint not null,
    created_at timestamp null,
    updated_at timestamp null,
    primary key (teammate_id)
);
