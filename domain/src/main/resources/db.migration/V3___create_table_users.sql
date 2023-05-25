CREATE TABLE users
(
    id         uuid PRIMARY KEY,
    first_name TEXT    NOT NULL,
    last_name  TEXT    NOT NULL,
    username   TEXT    NOT NULL UNIQUE,
    password   TEXT    NOT NULL,
    role_id    INTEGER NOT NULL REFERENCES roles (id),
    email      TEXT    NOT NULL UNIQUE
);