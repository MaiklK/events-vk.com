CREATE TABLE if NOT EXISTS PUBLIC.cities
(
    id     INTEGER NOT NULL PRIMARY KEY,
    area   VARCHAR(255),
    region VARCHAR(255),
    title  VARCHAR(255)
    );

CREATE TABLE if NOT EXISTS PUBLIC.countries
(
    id    INTEGER NOT NULL PRIMARY KEY,
    title VARCHAR(255)
    );


CREATE TABLE if NOT EXISTS PUBLIC.region
(
    id    INTEGER NOT NULL
    PRIMARY KEY,
    title VARCHAR(255)
    );
