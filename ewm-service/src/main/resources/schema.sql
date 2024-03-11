CREATE TABLE IF NOT EXISTS locations
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    lat FLOAT,
    lon FLOAT
);
CREATE TABLE IF NOT EXISTS users
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR,
    email VARCHAR,
    CONSTRAINT uq_mail UNIQUE (email)
);
CREATE TABLE IF NOT EXISTS categories
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR,
    CONSTRAINT uq_category UNIQUE (name)
);
CREATE TABLE IF NOT EXISTS events
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    annotation         VARCHAR(2000),
    category_id        BIGINT,
    created            TIMESTAMP WITHOUT TIME ZONE,
    description        VARCHAR(7000),
    event_date         TIMESTAMP WITHOUT TIME ZONE,
    initiator_id       BIGINT,
    location_id        BIGINT,
    paid               BOOLEAN,
    participant_limit  INTEGER,
    published          TIMESTAMP WITHOUT TIME ZONE,
    request_moderation BOOLEAN,
    state              VARCHAR(10),
    title              VARCHAR(120),
    CONSTRAINT fk_events_users FOREIGN KEY (initiator_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_events_categories FOREIGN KEY (category_id) REFERENCES categories (id),
    CONSTRAINT fk_events_locations FOREIGN KEY (location_id) REFERENCES locations (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS requests
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    event_id     BIGINT,
    requester_id BIGINT,
    created      TIMESTAMP WITHOUT TIME ZONE,
    status       VARCHAR(10),
    CONSTRAINT fk_requests_events FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE,
    CONSTRAINT fk_requests_users FOREIGN KEY (requester_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT unique_user_and_event UNIQUE (event_id, requester_id)
);

CREATE TABLE IF NOT EXISTS compilations
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    pinned boolean,
    title  VARCHAR(50)
    );

CREATE TABLE IF NOT EXISTS compilations_events
(
    compilation_id BIGINT,
    event_id       BIGINT,
    CONSTRAINT fk_compilations_events FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE,
    CONSTRAINT fk_events_compilations FOREIGN KEY (compilation_id) REFERENCES compilations (id) ON DELETE CASCADE,
    PRIMARY KEY (event_id, compilation_id)
);