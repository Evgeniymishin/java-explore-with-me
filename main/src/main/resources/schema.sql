DROP TABLE IF EXISTS users, categories, locations, events, requests, compilations, compilations_events, comments;

CREATE TABLE IF NOT EXISTS users (
    id                  BIGINT              GENERATED           BY DEFAULT AS   IDENTITY    NOT NULL,
    email               VARCHAR(255)        NOT NULL,
    name                VARCHAR(255)        NOT NULL,
    CONSTRAINT          uq_user_email       UNIQUE (email),
    CONSTRAINT          pk_user             PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS categories (
    id                  BIGINT              GENERATED           BY DEFAULT AS   IDENTITY    NOT NULL,
    name                VARCHAR(255)        NOT NULL,
    CONSTRAINT          uq_category_name    UNIQUE (name),
    CONSTRAINT          pk_category         PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS locations (
    id                  BIGINT              GENERATED           BY DEFAULT AS   IDENTITY    NOT NULL,
    lat                 FLOAT               NOT NULL,
    lon                 FLOAT               NOT NULL,
    CONSTRAINT          pk_location         PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS events (
    id                  BIGINT              GENERATED           BY DEFAULT AS   IDENTITY     NOT NULL,
    category_id         BIGINT              NOT NULL            REFERENCES      categories   (id),
    location_id         BIGINT              NOT NULL            REFERENCES      locations    (id),
    participant_limit   BIGINT              NOT NULL,
    user_id             BIGINT              NOT NULL            REFERENCES      users        (id),
    paid                BOOLEAN             NOT NULL,
    request_moderation  BOOLEAN             NOT NULL,
    annotation          VARCHAR(2000)       NOT NULL,
    description         VARCHAR(7000)       NOT NULL,
    state               VARCHAR(64)        NOT NULL,
    title               VARCHAR(120)        NOT NULL,
    created_on          TIMESTAMP           NOT NULL,
    event_date          TIMESTAMP           NOT NULL,
    published_on        TIMESTAMP,
    CONSTRAINT          pk_event            PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS requests (
    id                  BIGINT              GENERATED           BY DEFAULT AS   IDENTITY    NOT NULL,
    requester_id        BIGINT              NOT NULL            REFERENCES      users       (id),
    event_id            BIGINT              NOT NULL            REFERENCES      events      (id),
    status              VARCHAR(255)        NOT NULL,
    created             TIMESTAMP           NOT NULL,
    CONSTRAINT          pk_request          PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS compilations (
    id                  BIGINT              GENERATED           BY DEFAULT  AS  IDENTITY    NOT NULL,
    pinned              BOOLEAN             NOT NULL,
    title               VARCHAR(255)        NOT NULL,
    CONSTRAINT          pk_compilation     PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS compilations_events (
    compilation_id      BIGINT              NOT NULL            REFERENCES      compilations (id),
    event_id            BIGINT              NOT NULL            REFERENCES      events       (id),
    CONSTRAINT          pk_comp_ev          PRIMARY KEY (compilation_id, event_id)
);

CREATE TABLE IF NOT EXISTS comments (
    id                  BIGINT              GENERATED           BY DEFAULT AS   IDENTITY     NOT NULL,
    author_id           BIGINT              NOT NULL            REFERENCES      users        (id),
    event_id            BIGINT              NOT NULL            REFERENCES      events       (id),
    text                VARCHAR(10000)      NOT NULL,
    created             TIMESTAMP           NOT NULL,
    updated             TIMESTAMP           NOT NULL,
    CONSTRAINT          pk_comment          PRIMARY KEY (id)
);