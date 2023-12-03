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
    gender              char             null,
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

