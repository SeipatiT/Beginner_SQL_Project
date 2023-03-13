package wethinkcode.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Exercise 3.3
 */
public class Finder {

    private final Connection connection;

    /**
     * Create an instance of the Finder object using the provided database connection
     *
     * @param connection The JDBC connection to use
     */
    public Finder(Connection connection) {
        this.connection = connection;
    }

    /**
     * 3.3 (part 1) Complete this method
     * <p>
     * Finds all genres in the database
     *
     * @return a list of `Genre` objects
     * @throws SQLException the query failed
     */
    public List<Genre> findAllGenres() throws SQLException {
        String code;
        String description;
        List<Genre> genresList = new ArrayList<>();

        try(final PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM Genres"
        )){
            final boolean gotAResultSet = stmt.execute();

            if( ! gotAResultSet ){
                throw new RuntimeException( "Expected a SQL result set, but we got an update count instead!" );
            }
            try( ResultSet results = stmt.getResultSet() ){
                while( results.next() ){
                    code = results.getString( "code" );
                    description = results.getString( "description" );
                    genresList.add(new Genre(code,description));
                }
            }
        }
        return genresList;

    }

    /**
     * 3.3 (part 2) Complete this method
     * <p>
     * Finds all genres in the database that have specific substring in the description
     *
     * @param pattern The pattern to match
     * @return a list of `Genre` objects
     * @throws SQLException the query failed
     */
    public List<Genre> findGenresLike(String pattern) throws SQLException {
        String code;
        String description;
        List<Genre> genresList = new ArrayList<>();

        try(final PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM Genres WHERE code or description LIKE '%" + pattern + "%'"

        )){
            final boolean gotAResultSet = stmt.execute();

            if( ! gotAResultSet ){
                throw new RuntimeException( "Expected a SQL result set, but we got an update count instead!" );
            }
            try( ResultSet results = stmt.getResultSet() ){
                while( results.next() ){
                    code = results.getString( "code" );
                    description = results.getString( "description" );
                    genresList.add(new Genre(code,description));
                }
            }
        }
        return genresList;
    }

    /**
     * 3.3 (part 3) Complete this method
     * <p>
     * Finds all books with their genres
     *
     * @return a list of `BookGenreView` objects
     * @throws SQLException the query failed
     */
    public List<BookGenreView> findBooksAndGenres() throws SQLException {
        String title;
        String description;
        List<BookGenreView> bookList = new ArrayList<>();

        try(final PreparedStatement stmt = connection.prepareStatement(
                "SELECT Books.title, Genres.description FROM Books, Genres WHERE Books.genre_code = Genres.code"

        )){
            final boolean gotAResultSet = stmt.execute();

            if( ! gotAResultSet ){
                throw new RuntimeException( "Expected a SQL resultset, but we got an update count instead!" );
            }
            try( ResultSet results = stmt.getResultSet() ){
                while( results.next() ){
                    title = results.getString( "title" );
                    description = results.getString( "description" );
                    bookList.add(new BookGenreView(title,description));
                }
            }
        }
        return bookList;
    }


    /**
     * 3.3 (part 4) Complete this method
     * <p>
     * Finds the number of books in a genre
     *
     * @return the number of books in the genre
     * @throws SQLException the query failed
     */
    public int findNumberOfBooksInGenre(String genreCode) throws SQLException {
        int count = 0;

        try(final PreparedStatement stmt = connection.prepareStatement(
                "SELECT COUNT(*) FROM Books WHERE Books.genre_code = '"+ genreCode + "'"

        )){
            final boolean gotAResultSet = stmt.execute();

            if( ! gotAResultSet ){
                throw new RuntimeException( "Expected a SQL resultset, but we got an update count instead!" );
            }
            try( ResultSet results = stmt.getResultSet() ){
                while( results.next() ){
                    count = results.getInt( "count(*)" );
                }
            }
        }
        return count;
    }
}

