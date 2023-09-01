drop table IF EXISTS stats;

create TABLE IF NOT EXISTS stats (
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    app         VARCHAR(255)    NOT NULL,
    uri         VARCHAR(255)    NOT NULL,
    ip          VARCHAR(255)    NOT NULL,
    timestamp   TIMESTAMP       WITHOUT TIME ZONE,
    CONSTRAINT pk_stats PRIMARY KEY (id)
);