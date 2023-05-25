CREATE TABLE songs
(
    id           uuid PRIMARY KEY NOT NULL,
    title        TEXT             NOT NULL,
    release_date DATE,
    youtube_link TEXT,
    genre_id     INTEGER          NOT NULL REFERENCES genres (id),
    artist_id    uuid             NOT NULL REFERENCES artists (id)
)