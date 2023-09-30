CREATE TABLE if NOT EXISTS PUBLIC.dictionary_requests
(
    id      BIGINT NOT NULL PRIMARY KEY,
    request VARCHAR(255)
    );

CREATE SEQUENCE if NOT EXISTS PUBLIC.dictionary_requests_seq
    INCREMENT BY 50;
