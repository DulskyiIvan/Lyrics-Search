CREATE TABLE lyrics
(
    id            uuid PRIMARY KEY NOT NULL,
    text          text             NOT NULL,
    created_at     DATE             NOT NULL,
    song_id       uuid             NOT NULL REFERENCES songs (id),
    created_by_id uuid             NOT NULL REFERENCES users (id)
)