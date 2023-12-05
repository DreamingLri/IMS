create table user
(
    id                  int auto_increment
        primary key,
    net_id              varchar(255)     null,
    username            varchar(50)      null,
    password            varchar(255)     null,
    address             varchar(50)      null,
    code                varchar(20)      null,
    identification_code varchar(18)      null,
    birthday            date             null,
    gender              varchar(255)     null,
    entry_time          datetime         null,
    finish_time         datetime         null,
    create_time         datetime         null,
    create_by           varchar(255)     null,
    update_time         datetime         null,
    update_by           varchar(255)     null,
    del_flag            char default '0' null,
    constraint user_code
        unique (code)
);

create table courses
(
    id             int auto_increment
        primary key,
    name           varchar(50)      null,
    start_time     datetime         null,
    end_time       datetime         null,
    student_number int              null,
    credit         double(10, 2)    null,
    opened_by      varchar(50)      null,
    create_time    datetime         null,
    create_by      varchar(255)     null,
    update_time    datetime         null,
    update_by      varchar(255)     null,
    del_flag       char default '0' null
);

create table user_course
(
    net_id    varchar(255) null,
    course_id int          null
);

create table level
(
    id         int auto_increment
        primary key,
    level_name varchar(255) null,
    level      varchar(255) null
);

create table user_level
(
    net_id   varchar(255) null,
    level_id int          null,
    constraint user_level_pk
        unique (net_id, level_id)
);
