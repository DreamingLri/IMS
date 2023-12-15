create table course_time
(
    id        int auto_increment
        primary key,
    course_id int          null,
    weekday   varchar(255) null,
    session   varchar(255) null
);

create table courses
(
    id                int auto_increment
        primary key,
    name              varchar(50)      null,
    teacher           varchar(255)     null,
    start_time        datetime         null,
    end_time          datetime         null,
    student_number    int              null,
    credit            double(10, 2)    null,
    period double null,
    place             varchar(255)     null,
    course_assessment varchar(255)     null,
    opened_by         varchar(50)      null,
    create_time       datetime         null,
    create_by         varchar(255)     null,
    update_time       datetime         null,
    update_by         varchar(255)     null,
    del_flag          char default '0' null
);

create table exams
(
    id            int auto_increment
        primary key,
    name          varchar(255) null,
    date          datetime     null,
    start_time    datetime     null,
    end_time      datetime     null,
    exam_place    varchar(255) null,
    exam_duration double       null,
    invigilator   varchar(255) null,
    course_id     int          null,
    create_by     varchar(255) null,
    create_time   datetime     null,
    update_by     varchar(255) null,
    update_time   datetime     null,
    constraint exams_pk2
        unique (course_id)
);

create table leave_table
(
    id           int auto_increment
        primary key,
    user_id      int          null,
    leave_begin  datetime     null,
    leave_end    datetime     null,
    leave_reason varchar(255) null,
    leave_time   int          null,
    leave_type   varchar(255) null,
    accepted     varchar(255) null
);

create table level
(
    id         int auto_increment
        primary key,
    level_name varchar(255) null,
    level      varchar(255) null
);

create table micro_message
(
    id             int auto_increment
        primary key,
    from_user_id   int          null,
    from_user_name varchar(255) null,
    to_user_id     int          null,
    to_user_name   varchar(255) null,
    message        varchar(255) null
);

create table score
(
    id                    int auto_increment
        primary key,
    user_id               int          null,
    course_id             int          null,
    exam_score            int          null,
    study_score           int          null,
    total_score           double       null,
    grade_point           double       null,
    score_function        int          null,
    evaluation_score      int          null,
    evaluation_suggestion varchar(255) null
);

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
    birthday            datetime         null,
    gender              varchar(255)     null,
    entry_time          datetime         null,
    finish_time         datetime         null,
    total_credit        double           null,
    earned_credit       double           null,
    grade_points        double           null,
    affiliated_school   varchar(255)     null,
    evaluation_score    double           null,
    qualification       varchar(255)     null,
    research_direction  varchar(255)     null,
    create_time         datetime         null,
    create_by           varchar(255)     null,
    update_time         datetime         null,
    update_by           varchar(255)     null,
    del_flag            char default '0' null,
    constraint user_code
        unique (code)
);

create table user_course
(
    user_id   int null,
    course_id int null,
    constraint user_course_pk
        unique (course_id, user_id)
);

create table user_exams
(
    user_id int null,
    exam_id int null
);

create table user_level
(
    user_id     int              null,
    level_id    int              null,
    `del _flag` char default '0' null,
    constraint user_level_pk
        unique (user_id, level_id)
);

