CREATE TABLE if NOT EXISTS PUBLIC.dic_requests
(
    id          INTEGER NOT NULL PRIMARY KEY,
    req         CHAR UNIQUE
    );

CREATE SEQUENCE if NOT EXISTS PUBLIC.dic_requests_seq
    INCREMENT BY 50;
