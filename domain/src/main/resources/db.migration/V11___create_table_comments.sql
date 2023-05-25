CREATE TABLE comments
(
    id           uuid PRIMARY KEY NOT NULL,
    comment_text text             NOT NULL,
    created_at   date             NOT NULL,
    user_id      uuid             NOT NULL REFERENCES users (id),
    song_id      uuid             NOT NULL REFERENCES songs (id)
)