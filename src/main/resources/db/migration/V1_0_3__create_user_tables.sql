CREATE TABLE if NOT EXISTS PUBLIC.users
(
    is_account_non_locked BOOLEAN,
    is_closed             BOOLEAN,
    sex                   INTEGER,
    "access token"        VARCHAR(255),
    birthday_date         VARCHAR(30),
    first_name            VARCHAR(128),
    last_name             VARCHAR(128),
    mobile_phone          VARCHAR(20),
    password              VARCHAR(255),
    photo_big             VARCHAR(255),
    photo_id              VARCHAR(30),
    vkid                  VARCHAR(30) NOT NULL PRIMARY KEY
    );

CREATE TABLE if NOT EXISTS PUBLIC.user_counters
(
    user_id         VARCHAR(30) NOT NULL PRIMARY KEY REFERENCES PUBLIC.users,
    albums          INTEGER,
    audios          INTEGER,
    clips_followers INTEGER,
    followers       INTEGER,
    friends         INTEGER,
    gifts           INTEGER,
    "groups"        INTEGER,
    pages           INTEGER,
    photos          INTEGER,
    videos          INTEGER
);

CREATE TABLE if NOT EXISTS PUBLIC.user_personal
(
    user_id     VARCHAR(30) NOT NULL PRIMARY KEY REFERENCES PUBLIC.users,
    alcohol     INTEGER,
    life_main   INTEGER,
    people_main INTEGER,
    political   INTEGER,
    smoking     INTEGER,
    inspire_by  VARCHAR(128)
    );

CREATE TABLE if NOT EXISTS PUBLIC.user_langs
(
    user_personal_id VARCHAR(30) NOT NULL CONSTRAINT FKJWRRQU9GH9HFT56PQFRR9GVRT REFERENCES PUBLIC.user_personal,
    lang             VARCHAR(128)
    );

CREATE TABLE If NOT EXISTS PUBLIC.roles
(
    id      BIGINT NOT NULL PRIMARY KEY,
    "name"  VARCHAR(128) UNIQUE
    );


CREATE SEQUENCE if NOT EXISTS PUBLIC.roles_seq
    INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS PUBLIC.user_role
(
    role_id BIGINT NOT NULL CONSTRAINT FK66OU45FYYDGLTRHVUC81RP15Q REFERENCES PUBLIC.roles,
    user_id VARCHAR(30) NOT NULL CONSTRAINT FKM3TMF7S11ILNY7LVPIKS60J0N REFERENCES PUBLIC.users,
    PRIMARY KEY (role_id, user_id)
    );

CREATE TABLE if NOT EXISTS PUBLIC.user_city
(
    city_id     INTEGER CONSTRAINT FKRWXU616SGX5R2L7STVF4HJR4V REFERENCES PUBLIC.cities,
    user_id     VARCHAR(30) NOT NULL PRIMARY KEY CONSTRAINT FK6EMBKCEA1YF9FQC5UW27SH22I REFERENCES PUBLIC.users
);

CREATE TABLE if NOT EXISTS PUBLIC.user_country
(
    country_id   INTEGER CONSTRAINT FKLSML2J6NCX2W0Q5FCSI1I7GJR REFERENCES PUBLIC.COUNTRIES,
    user_id      VARCHAR(30) NOT NULL PRIMARY KEY CONSTRAINT FK3M1QVQI0NDRB3OBPI1R1ICSMG REFERENCES PUBLIC.users
);

CREATE TABLE if NOT EXISTS PUBLIC.access_tokens
(
    id        VARCHAR(30) NOT NULL PRIMARY KEY,
    is_in_use BOOLEAN,
    is_valid  BOOLEAN,
    token     varchar(255)
);