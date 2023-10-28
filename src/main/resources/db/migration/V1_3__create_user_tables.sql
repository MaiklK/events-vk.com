CREATE TABLE if NOT EXISTS PUBLIC.users
(
    id                    BIGINT NOT NULL PRIMARY KEY,
    is_account_non_locked BOOLEAN,
    is_closed             BOOLEAN,
    sex                   INTEGER,
    "access token"        VARCHAR(255),
    birthday_date         VARCHAR(255),
    first_name            VARCHAR(255),
    last_name             VARCHAR(255),
    mobile_phone          VARCHAR(255),
    password              VARCHAR(255),
    photo_big             VARCHAR(255),
    photo_id              VARCHAR(255),
    vkid                  VARCHAR(255)
    );

CREATE SEQUENCE if NOT EXISTS users_seq
    INCREMENT BY 50;

CREATE TABLE if NOT EXISTS PUBLIC.user_counters
(
    id              BIGINT NOT NULL PRIMARY KEY,
    user_id         BIGINT UNIQUE CONSTRAINT FKBCRGTUWRWMUNY05A8D3NU6YEI REFERENCES PUBLIC.users,
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

CREATE SEQUENCE if NOT EXISTS user_counters_seq
    INCREMENT BY 50;

CREATE TABLE if NOT EXISTS PUBLIC.user_personal
(
    user_id     BIGINT UNIQUE CONSTRAINT FKPL4TCGBO02PCKF8FGR16S644I REFERENCES PUBLIC.users,
    id          BIGINT NOT NULL PRIMARY KEY,
    alcohol     INTEGER,
    life_main   INTEGER,
    people_main INTEGER,
    political   INTEGER,
    smoking     INTEGER,
    inspire_by  VARCHAR(255)
    );

CREATE SEQUENCE if NOT EXISTS user_personal_seq
    INCREMENT BY 50;

CREATE TABLE if NOT EXISTS PUBLIC.user_langs
(
    user_personal_id BIGINT NOT NULL CONSTRAINT FKJWRRQU9GH9HFT56PQFRR9GVRT REFERENCES PUBLIC.user_personal,
    lang             VARCHAR(255)
    );

CREATE TABLE If NOT EXISTS PUBLIC.roles
(
    id      BIGINT NOT NULL PRIMARY KEY,
    "name"  VARCHAR(255) UNIQUE
    );


CREATE SEQUENCE if NOT EXISTS PUBLIC.roles_seq
    INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS PUBLIC.user_role
(
    role_id BIGINT NOT NULL CONSTRAINT FK66OU45FYYDGLTRHVUC81RP15Q REFERENCES PUBLIC.roles,
    user_id BIGINT NOT NULL CONSTRAINT FKM3TMF7S11ILNY7LVPIKS60J0N REFERENCES PUBLIC.users,
    PRIMARY KEY (role_id, user_id)
    );

CREATE TABLE if NOT EXISTS PUBLIC.user_city
(
    city_id  INTEGER CONSTRAINT FKRWXU616SGX5R2L7STVF4HJR4V REFERENCES PUBLIC.cities,
    user_id BIGINT NOT NULL PRIMARY KEY CONSTRAINT FK6EMBKCEA1YF9FQC5UW27SH22I REFERENCES PUBLIC.users
);

CREATE TABLE if NOT EXISTS PUBLIC.user_country
(
    country_id INTEGER CONSTRAINT FKLSML2J6NCX2W0Q5FCSI1I7GJR REFERENCES PUBLIC.COUNTRIES,
    user_id   BIGINT NOT NULL PRIMARY KEY CONSTRAINT FK3M1QVQI0NDRB3OBPI1R1ICSMG REFERENCES PUBLIC.users
);

CREATE TABLE if NOT EXISTS PUBLIC.access_tokens
(
    id        BIGINT NOT NULL PRIMARY KEY,
    is_in_use BOOLEAN,
    is_valid  BOOLEAN,
    token     varchar(255)
);