CREATE TABLE users
(
    id         uuid PRIMARY KEY,
    first_name TEXT,
    last_name  TEXT,
    username   TEXT NOT NULL UNIQUE,
    password   TEXT,
    role_id    INTEGER REFERENCES roles (id),
    email      TEXT UNIQUE
);