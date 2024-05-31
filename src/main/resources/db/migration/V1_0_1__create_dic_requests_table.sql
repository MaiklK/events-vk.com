CREATE TABLE if NOT EXISTS PUBLIC.dic_requests
(
    id          INTEGER NOT NULL PRIMARY KEY,
    req         VARCHAR(5) UNIQUE
    );

CREATE SEQUENCE if NOT EXISTS PUBLIC.dic_requests_seq
    INCREMENT BY 50;
