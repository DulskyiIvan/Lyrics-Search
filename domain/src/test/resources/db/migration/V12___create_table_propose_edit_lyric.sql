CREATE TABLE propose_edit_lyric
(
    id           uuid PRIMARY KEY NOT NULL,
    lyric_text text             NOT NULL,
    created_at date NOT NULL,
    user_id      uuid             NOT NULL REFERENCES users (id),
    song_id      uuid             NOT NULL REFERENCES songs (id)
)