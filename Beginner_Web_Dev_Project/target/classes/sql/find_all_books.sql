SELECT title,genre_code,code, description

FROM Books b, Genres g

WHERE b.genre_code = g.code;