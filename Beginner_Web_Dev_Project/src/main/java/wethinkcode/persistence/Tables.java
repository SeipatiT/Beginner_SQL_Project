package wethinkcode.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Exercise 3.1
 */
public class Tables {
    private final Connection connection;

    /**
     * Create an instance of the Tables object using the provided database connection
     * @param connection The JDBC connection to use
     */
    public Tables(Connection connection) {
        this.connection = connection;
    }

    /**
     * 3.1 Complete this method
     *
     * Create the Genres table
     * @return true if the table was successfully created, otherwise false
     */
    public boolean createGenres() {
        String genres_sql = "CREATE TABLE \"Genres\" (\n" +
                "\n" +
                "    code TEXT NOT NULL,\n" +
                "    description TEXT NOT NULL,\n" +
                "    PRIMARY KEY (code, description)\n" +
                ")";
        return createTable(genres_sql);
    }

    /**
     * 3.1 Complete this method
     *
     * Create the Books table
     * @return true if the table was successfully created, otherwise false
     */
    public boolean createBooks() {
        createGenres();
        String books_sql ="CREATE TABLE \"Books\" (\n" +

                "    id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "    title TEXT NOT NULL,\n" +
                "    genre_code TEXT NOT NULL,\n" +
                "    FOREIGN KEY (genre_code) REFERENCES Genres(code)\n" +
                ")";
        return createTable(books_sql);
    }

    /**
     * 3.1 Complete this method
     *
     * Execute a SQL statement containing an SQL command to create a table.
     * If the SQL statement is not a create statement, it should return false.
     *
     * @param sql the SQL statement containing the create command
     * @return true if the command was successfully executed, else false
     */
    protected boolean createTable(String sql) {
        try (final Statement stmt = connection.createStatement()){
        if(sql.contains("CREATE")){
            stmt.execute(sql);
            return true;
        }
        return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
