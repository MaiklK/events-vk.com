CREATE TABLE if NOT EXISTS PUBLIC.events
(
    id                VARCHAR(255) NOT NULL PRIMARY KEY,
    "name"            VARCHAR(255),
    city_id           INTEGER,
    finish_date       INTEGER,
    has_photo         INTEGER,
    members_count     INTEGER,
    start_date        INTEGER,
    activity          VARCHAR(255),
    description       VARCHAR(255),
    event_vkid        VARCHAR(255),
    public_date_label VARCHAR(255),
    screen_name       VARCHAR(255),
    site              VARCHAR(255),
    status            VARCHAR(255)
);