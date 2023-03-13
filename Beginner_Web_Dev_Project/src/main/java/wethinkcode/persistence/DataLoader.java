package wethinkcode.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Exercise 3.2
 */
public class DataLoader {
    private final Connection connection;

    /**
     * These are the Genres that must be persisted to the database
     */
    private final Map<String, Genre> genres = Map.of(
            "PROG", new Genre("PROG", "Programming"),
            "BIO", new Genre("BIO", "Biography"),
            "SCIFI", new Genre("SCIFI", "Science Fiction"));

    /**
     * These are the Books that must be persisted to the database
     */
    private final List<Book> books = List.of(
            new Book("Test Driven Development", genres.get("PROG")),
            new Book("Programming in Haskell", genres.get("PROG")),
            new Book("Scatterlings of Africa", genres.get("BIO")));

    /**
     * Create an instance of the DataLoader object using the provided database connection
     *
     * @param connection The JDBC connection to use
     */
    public DataLoader(Connection connection) {
        this.connection = connection;
    }

    /**
     * 3.2 (part 1) Complete this method
     * <p>
     * Inserts data from the `Genres` collection into the `Genres` table.
     *
     * @return true if the data was successfully inserted, otherwise false
     */
    public boolean insertGenres() {
        for (Genre value : genres.values()) {
            try (final PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO Genres(code, description) VALUES (?, ?)"
            )) {
                stmt.setString(1, value.getCode());
                stmt.setString(2, value.getDescription());
                final boolean gotAResultSet = stmt.execute();

                if (gotAResultSet) {
                    throw new RuntimeException( "Unexpectedly got a SQL result set." );
                } else {
                    final int updateCount = stmt.getUpdateCount();
                    if (updateCount != 1) {
                        return false;
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    /**
     * 3.2 (part 1) Complete this method
     * <p>
     * Inserts data from the `Books` collection into the `Books` table.
     *
     * @return true if the data was successfully inserted, otherwise false
     */
    public List<Book> insertBooks() throws SQLException {
        insertGenres();

        int book_id;

        List<Book> bookList = new ArrayList<>();

        for (Book book : books) {
            try (final PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO Books(title, genre_code) VALUES (?, ?)"
            )) {
                stmt.setString(1, book.getTitle());
                stmt.setString(2, book.getGenre().getCode());
                final boolean gotAResultSet = stmt.execute();

                if (gotAResultSet) {
                    throw new RuntimeException("Unexpectedly got a SQL resultset.");
                } else {
                    final int updateCount = stmt.getUpdateCount();
                    if (updateCount == 1) {
                        book_id = getGeneratedId(stmt);
                        book.assignId(book_id);
                        bookList.add(book);
                    } else {
                        throw new RuntimeException("Expected 1 row to be inserted, but got " + updateCount);
                    }
                }
            }
        }
        return bookList;
    }

    /**
     * Get the last id generated from the prepared statement
     *
     * @param s the prepared statement
     * @return the last id generated
     * @throws SQLException if the id was not generated
     */
    private int getGeneratedId(PreparedStatement s) throws SQLException {
        ResultSet generatedKeys = s.getGeneratedKeys();
        if (!generatedKeys.next()) throw new SQLException("Id was not generated");
        return generatedKeys.getInt(1);
    }
}


