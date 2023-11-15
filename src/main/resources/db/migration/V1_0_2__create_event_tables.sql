CREATE TABLE if NOT EXISTS PUBLIC.events
(
    id                VARCHAR(128) NOT NULL PRIMARY KEY,
    "name"            VARCHAR(128),
    city_id           INTEGER,
    finish_date       INTEGER,
    has_photo         INTEGER,
    members_count     INTEGER,
    start_date        INTEGER,
    activity          VARCHAR(128),
    description       VARCHAR(128),
    event_vkid        VARCHAR(128),
    public_date_label VARCHAR(128),
    screen_name       VARCHAR(128),
    site              VARCHAR(128),
    status            VARCHAR(128)
);