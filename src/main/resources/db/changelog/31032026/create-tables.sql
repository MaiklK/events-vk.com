-- Создание последовательностей
create sequence public.black_list_seq increment by 1;
alter sequence public.black_list_seq owner to postgres;

create sequence public.dic_request_seq increment by 1;
alter sequence public.dic_request_seq owner to postgres;

create sequence public.user_role_seq increment by 1;
alter sequence public.user_role_seq owner to postgres;

create sequence public.white_list_seq increment by 1;
alter sequence public.white_list_seq owner to postgres;

-- Создание таблицы access_token
create table public.access_token
(
    id        bigint       not null primary key,
    is_in_use boolean,
    is_valid  boolean,
    token     varchar(255)
);

alter table public.access_token owner to postgres;

-- Создание таблицы allowed_city
create table public.allowed_city
(
    city_id   bigint       not null primary key,
    city_name varchar(50),
    country_id bigint
);

alter table public.allowed_city owner to postgres;


-- Создание таблицы black_list
create table public.black_list
(
    id   bigint       not null primary key DEFAULT nextval('black_list_seq'),
    name varchar(20) constraint uk8p2ejiir70sybu2spdc2ip2u9 unique
);

alter table public.black_list owner to postgres;

-- Создание таблицы cities
create table public.cities
(
    id    bigint       not null primary key,
    title varchar(50) constraint uk_cities_title unique
);

alter table public.cities owner to postgres;

create unique index idx_cities_title on public.cities (title);

-- Создание таблицы dic_request
create table public.dic_request
(
    id  integer      not null primary key DEFAULT nextval('dic_request_seq'),
    req varchar(50) constraint ukt67x7ldn5adc7wcqwvf7ytd5c unique
);

alter table public.dic_request owner to postgres;

-- Создание таблицы event
create table public.event
(
    event_vk_id       bigint       not null primary key,
    activity          varchar(50),
    city_id           integer,
    deactivated       varchar(255),
    description       varchar(10000),
    finish_date       integer,
    has_photo         integer,
    is_closed         integer,
    members_count     integer,
    name              varchar(50),
    public_date_label varchar(50),
    screen_name       varchar(50),
    site              varchar(255),
    start_date        integer,
    status            varchar(50)
);

alter table public.event owner to postgres;

-- Создание таблицы user_counter
create table public.user_counter
(
    user_vk_id       bigint not null primary key,
    albums           integer,
    audios           integer,
    clips_followers  bigint,
    followers        integer,
    friends          integer,
    gifts            integer,
    groups           integer,
    pages            integer,
    photos           integer,
    videos           integer
);

alter table public.user_counter owner to postgres;

-- Создание таблицы user_personal
create table public.user_personal
(
    user_vk_id  bigint       not null primary key,
    alcohol     integer,
    inspired_by varchar(20),
    life_main   integer,
    people_main integer,
    political   integer,
    smoking     integer
);

alter table public.user_personal owner to postgres;

-- Создание таблицы vk_users
create table public.vk_users
(
    user_vk_id    bigint       not null primary key,
    birthday_date varchar(20),
    city_id       bigint,
    first_name    varchar(30),
    is_closed     boolean,
    is_locked     boolean,
    last_name     varchar(30),
    password      varchar(50),
    photo_big     varchar(255),
    photo_id      varchar(50),
    sex           integer
);

alter table public.vk_users owner to postgres;

-- Создание таблицы user_role (справочник ролей)
create table public.user_role
(
    id   bigint       not null primary key,
    name varchar(50)  not null unique
);

alter table public.user_role owner to postgres;

-- Создание таблицы id_role (связь пользователь-роль)
create table public.id_role
(
    user_id bigint not null references public.vk_users (user_vk_id),
    role_id bigint not null references public.user_role (id),
    primary key (user_id, role_id)
);

alter table public.id_role owner to postgres;

-- Создание таблицы white_list
create table public.white_list
(
    id   bigint       not null primary key DEFAULT nextval('white_list_seq'),
    name varchar(50)
);

alter table public.white_list owner to postgres;