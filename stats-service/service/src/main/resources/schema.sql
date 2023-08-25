CREATE TABLE IF NOT EXISTS hits
(
    id
    BIGINT
    GENERATED
    ALWAYS AS
    IDENTITY
    PRIMARY
    KEY
    UNIQUE,
    app
    VARCHAR
(
    100
) NOT NULL,
    uri VARCHAR
(
    320
) NOT NULL,
    ip VARCHAR
(
    320
) NOT NULL,
    timestamp TIMESTAMP WITHOUT TIME ZONE
    );