create table client
(
    client_id      bigint auto_increment,
    member_id      bigint       null,
    account_holder varchar(255) null,
    account_number varchar(255) null,
    bank           varchar(255) null,
    detail         varchar(255) null,
    email          varchar(255) null,
    street         varchar(255) null,
    zipcode        varchar(255) null,
    created_at     timestamp    null,
    updated_at     timestamp    null,
    primary key (client_id)
);

create table file_log
(
    file_log_id bigint auto_increment,
    member_id   bigint       null,
    file_name   varchar(255) null,
    uploaded_at timestamp    null,
    primary key (file_log_id)
);

create table if not exists member
(
    member_id         bigint auto_increment,
    email             varchar(255)                                                      null,
    member_type       enum ('ROLE_ADMIN', 'ROLE_CLIENT', 'ROLE_MANAGER', 'ROLE_PEOPLE') null,
    nickname          varchar(255)                                                      null,
    password          varchar(255)                                                      null,
    phone_number      varchar(255)                                                      null,
    profile_image_url varchar(255)                                                      null,
    created_at        timestamp                                                         null,
    updated_at        timestamp                                                         null,
    primary key (member_id),
    constraint uq_email unique (email),
    constraint uq_nickname unique (nickname)
);

create table member_service_term_agreement
(
    agreed           bit          null,
    agreed_at        datetime(6)  null,
    member_id        bigint       not null,
    service_term_tag varchar(255) null,
    constraint fk_member_service_term_agreement_member
        foreign key (member_id) references member (member_id)
);

create table people
(
    people_id     bigint auto_increment,
    member_id     bigint                      null,
    activity_area varchar(255)                null,
    email         varchar(255)                null,
    introduction  varchar(255)                null,
    major         varchar(255)                null,
    people_type   enum ('INDIVIDUAL', 'TEAM') null,
    school        varchar(255)                null,
    created_at    timestamp                   null,
    updated_at    timestamp                   null,
    primary key (people_id)
);

create table people_profile_hashtag
(
    people_id bigint       not null,
    hashtags  varchar(255) null,
    constraint fk_people_profile_hashtag_people
        foreign key (people_id) references people (people_id)
);

create table people_profile_portfolio
(
    people_id   bigint       not null,
    description varchar(255) null,
    url         varchar(255) null,
    constraint fk_people_profile_portfolio_people
        foreign key (people_id) references people (people_id)
);

create table people_profile_tech_stack
(
    people_id   bigint       not null,
    tech_stacks varchar(255) null,
    constraint fk_people_profile_tech_stack_people
        foreign key (people_id) references people (people_id)
);

create table people_like
(
    people_like_id bigint auto_increment,
    people_id      bigint    null,
    client_id      bigint    null,
    created_at     timestamp null,
    primary key (people_like_id)
);

create table project
(
    project_id             bigint auto_increment,
    application_end_date   date                                                                    null,
    application_start_date date                                                                    null,
    estimated_end_date     date                                                                    null,
    estimated_start_date   date                                                                    null,
    payment                bigint                                                                  null,
    category               enum ('BACKEND', 'FRONTEND', 'PROGRAM', 'WEB')                          null,
    description            varchar(255)                                                            null,
    meeting_type           enum ('IN_PERSON', 'REMOTE')                                            null,
    status                 enum ('APPLYING', 'CANCELLED', 'COMPLETED', 'PREPARING', 'PROGRESSING') null,
    title                  varchar(255)                                                            null,
    client_id              bigint                                                                  null,
    created_at             timestamp                                                               null,
    updated_at             timestamp                                                               null,
    primary key (project_id)
);

create table project_attachment_file
(
    project_id       bigint       not null,
    attachment_files varchar(255) null,
    constraint fk_project_attachment_file_project
        foreign key (project_id) references project (project_id)
);

create table project_hashtag
(
    project_id bigint       not null,
    hashtags   varchar(255) null,
    constraint fk_project_hashtag_project
        foreign key (project_id) references project (project_id)
);

create table project_application
(
    project_application_id bigint auto_increment,
    expected_end_date      date                                                                                                                                             null,
    expected_start_date    date                                                                                                                                             null,
    description            varchar(255)                                                                                                                                     null,
    status                 enum ('APPLICATION_ACCEPTED', 'APPLICATION_COMPLETED', 'APPLICATION_REJECTED', 'APPLICATION_WITHDRAWN', 'PROCEEDING_MEETING', 'WAITING_MEETING') null,
    people_id              bigint                                                                                                                                           null,
    project_id             bigint                                                                                                                                           null,
    created_at             timestamp                                                                                                                                        null,
    updated_at             timestamp                                                                                                                                        null,
    primary key (project_application_id)
);

create table project_application_attachment_file
(
    project_application_id bigint       not null,
    attachment_files       varchar(255) null,
    constraint fk_project_application_attachment_file_project
        foreign key (project_application_id) references project_application (project_application_id)
);

create table project_like
(
    project_like_id bigint auto_increment,
    people_id       bigint    null,
    project_id      bigint    null,
    created_at      timestamp null,
    primary key (project_like_id)
);

create table project_meeting
(
    project_meeting_id bigint auto_increment,
    meeting_date       date         null,
    meeting_end_time   time(6)      null,
    meeting_start_time time(6)      null,
    description        varchar(255) null,
    location           varchar(255) null,
    phone_number       varchar(255) null,
    people_id          bigint       null,
    project_id         bigint       null,
    created_at         timestamp    null,
    updated_at         timestamp    null,
    primary key (project_meeting_id)
);

create table service_term
(
    service_term_tag varchar(255) not null,
    required         bit          null,
    revocable        bit          null,
    created_at       timestamp    null,
    updated_at       timestamp    null,
    primary key (service_term_tag)
);

