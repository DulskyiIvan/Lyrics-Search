CREATE TABLE views
(
    id          uuid PRIMARY KEY,
    song_id     uuid,
    viewed_date date,
    user_id     uuid,
    FOREIGN KEY (song_id) REFERENCES songs (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);