
CREATE TABLE Books(
id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,
title TEXT NOT NULL,
genre_code TEXT NOT NULL,
FOREIGN KEY (genre_code) REFERENCES Genres (code)

);

--
--CREATE TABLE child (
--    id           INTEGER PRIMARY KEY,
--    parent_id    INTEGER,
--    description  TEXT,
--    FOREIGN KEY (parent_id) REFERENCES parent(id)
--);