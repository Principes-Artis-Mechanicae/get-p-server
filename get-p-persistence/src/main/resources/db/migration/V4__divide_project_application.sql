alter table people drop column people_type;

alter table project_application add column dtype varchar(255) not null;
alter table project_application change column status status enum(
    'PENDING_TEAM_APPROVAL',
    'COMPLETED',
    'WAITING_MEETING',
    'MEETING_COMPLETED',
    'ACCEPTED',
    'CLOSED')
    null;

create table individual_project_application
(
    project_application_id bigint auto_increment,
    primary key (project_application_id)
);

create table team_project_application
(
    project_application_id bigint auto_increment,
    primary key (project_application_id)
);

create table team_project_application_team
(
    project_application_id bigint not null,
    teams                  bigint null
);
