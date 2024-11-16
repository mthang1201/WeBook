package org.uet.library_management.core.repositories;

import org.uet.library_management.ConnectJDBC;
import org.uet.library_management.core.entities.Bookmark;
import org.uet.library_management.core.entities.documents.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The BookmarkRepository class offers CRUD operations and query methods for bookmarks stored
 * in a MySQL database. This class implements the MySQLRepository interface for Bookmark entities.
 */
public class BookmarkRepository implements MySQLRepository<Bookmark> {
    private final String db_table;

    private final ConnectJDBC connectJDBC;

    /**
     * Constructs a new BookmarkRepository object.
     *
     * Initializes the database connection manager and sets the database table name
     * for the repository.
     */
    public BookmarkRepository() {
        connectJDBC = new ConnectJDBC();
        db_table = "bookmarks";
    }

    /**
     * Constructs a new BookmarkRepository object.
     *
     * Initializes the database connection manager and sets the database table name
     * for the repository.
     *
     * @param connectJDBC the ConnectJDBC object used to manage the database connection
     */
    public BookmarkRepository(ConnectJDBC connectJDBC) {
        this.connectJDBC = connectJDBC;
        db_table = "bookmarks";
    }

    /**
     * Populates a Bookmark object from a given ResultSet.
     *
     * @param rs the ResultSet containing the bookmark data
     * @return a Bookmark object populated with data from the ResultSet
     * @throws SQLException if any SQL errors occur
     */
    private Bookmark populateBookmark(ResultSet rs) throws SQLException {
        Bookmark bookmark = new Bookmark();
        bookmark.setUserId(rs.getInt("userId"));
        bookmark.setIsbn13(rs.getString("isbn13"));

        return bookmark;
    }

    /**
     * Retrieves all bookmarks from the database.
     *
     * @return a list of {@link Bookmark} objects
     * @throws RuntimeException if any SQL errors occur
     */
    @Override
    public List<Bookmark> findAll() {
        List<Bookmark> bookmarks = new ArrayList<>();
        String query = "SELECT * FROM " + db_table;

        try (ResultSet rs = connectJDBC.executeQuery(query)) {
            while (rs.next()) {
                bookmarks.add(populateBookmark(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return bookmarks;
    }

    /**
     * Retrieves a paginated list of bookmarks from the database.
     *
     * @param page the page number to retrieve, starting from 1
     * @param pageSize the number of bookmarks to retrieve per page
     * @return a list of {@link Bookmark} objects for the specified page
     */
    @Override
    public List<Bookmark> findAllByPage(int page, int pageSize) {
        List<Bookmark> bookmarks = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        String query = "SELECT * FROM " + db_table + " LIMIT " + pageSize + " OFFSET " + offset;

        try (ResultSet rs = connectJDBC.executeQuery(query)) {
            while (rs.next()) {
                bookmarks.add(populateBookmark(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return bookmarks;
    }

    /**
     * Counts all the records in the database table represented by this repository.
     *
     * @return the total number of records in the database table
     */
    @Override
    public int countAll() {
        String query = "SELECT COUNT(*) AS total FROM " + db_table;
        int count = 0;

        try (ResultSet rs = connectJDBC.executeQuery(query)) {
            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    /**
     * Retrieves a list of bookmarks for a given user and document from the database.
     *
     * @param userId the ID of the user
     * @param documentId the ID of the document
     * @return a list of {@link Bookmark} objects associated with the specified user and document
     * @throws RuntimeException if any SQL errors occur during the query execution
     */
    public List<Bookmark> findById(int userId, int documentId) {
        List<Bookmark> bookmarks = new ArrayList<>();
        String query = "SELECT * FROM " + db_table + " WHERE userId = ? AND documentId = ?";

        try (ResultSet rs = connectJDBC.executeQueryWithParams(query, userId, documentId)) {
            while (rs.next()) {
                bookmarks.add(populateBookmark(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return bookmarks;
    }

    /**
     * Retrieves a list of bookmarks for a given user from the database.
     *
     * @param userId the ID of the user whose bookmarks are to be retrieved
     * @return a list of {@link Bookmark} objects associated with the specified user
     * @throws RuntimeException if any SQL errors occur during the query execution
     */
    public List<Bookmark> findByUserId(int userId) {
        List<Bookmark> bookmarks = new ArrayList<>();
        String query = "SELECT * FROM " + db_table + " WHERE userId = ?";

        try (ResultSet rs = connectJDBC.executeQueryWithParams(query, userId)) {
            while (rs.next()) {
                bookmarks.add(populateBookmark(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return bookmarks;
    }

    /**
     * Adds a new bookmark to the database.
     *
     * @param bookmark the Bookmark object containing the user ID and ISBN-13 to be added.
     */
    @Override
    public void add(Bookmark bookmark) {
        String query = "INSERT INTO " + db_table + " (userId, isbn13) VALUES (?, ?)";
        connectJDBC.executeUpdate(query, bookmark.getUserId(), bookmark.getIsbn13());
    }

    /**
     * Updates an existing bookmark in the database.
     *
     * @param bookmark the Bookmark object containing the updated information. The userId and
     *                 isbn13 fields of the bookmark object will be used to update the database record.
     */
    @Override
    public void update(Bookmark bookmark) {
        String query = "UPDATE " + db_table + " SET isbn13 = ? WHERE userId = ?";
        connectJDBC.executeUpdate(query, bookmark.getIsbn13(), bookmark.getUserId());
    }

    /**
     * Removes a bookmark from the database associated with the given user ID and ISBN-13.
     *
     * @param bookmark the Bookmark object containing the user ID and ISBN-13 for the record to be removed
     */
    @Override
    public void remove(Bookmark bookmark) {
        String query = "DELETE FROM " + db_table + " WHERE userId = ? AND isbn13 = ?";
        connectJDBC.executeUpdate(query, bookmark.getUserId(), bookmark.getIsbn13());
    }

    /**
     * Removes all records from the database table associated with the BookmarkRepository.
     *
     * This method creates an SQL DELETE query to remove all records from the table specified
     * by the db_table field and executes the query using the connectJDBC object's executeUpdate method.
     *
     * Note: This method will delete all data from the table, so use it with caution.
     */
    @Override
    public void removeAll() {
        String query = "DELETE FROM " + db_table;
        connectJDBC.executeUpdate(query);
    }

    public void getConnectJDBC() {
        System.out.println(connectJDBC);
    }
}
