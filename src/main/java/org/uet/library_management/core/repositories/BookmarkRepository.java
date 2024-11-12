package org.uet.library_management.core.repositories;

import org.uet.library_management.ConnectJDBC;
import org.uet.library_management.core.entities.Bookmark;
import org.uet.library_management.core.entities.documents.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookmarkRepository implements MySQLRepository<Bookmark> {
    private final String db_table;

    private final ConnectJDBC connectJDBC;

    public BookmarkRepository() {
        connectJDBC = new ConnectJDBC();
        db_table = "bookmarks";
    }

    private Bookmark populateBookmark(ResultSet rs) throws SQLException {
        Bookmark bookmark = new Bookmark();
        bookmark.setUserId(rs.getInt("userId"));
        bookmark.setIsbn13(rs.getString("isbn13"));

        return bookmark;
    }

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

    @Override
    public void add(Bookmark bookmark) {
        String query = "INSERT INTO " + db_table + " (userId, isbn13) VALUES (?, ?)";
        connectJDBC.executeUpdate(query, bookmark.getUserId(), bookmark.getIsbn13());
    }

    @Override
    public void update(Bookmark bookmark) {
        String query = "UPDATE " + db_table + " SET isbn13 = ? WHERE userId = ?";
        connectJDBC.executeUpdate(query, bookmark.getIsbn13(), bookmark.getUserId());
    }

    @Override
    public void remove(Bookmark bookmark) {
        String query = "DELETE FROM " + db_table + " WHERE userId = ? AND isbn13 = ?";
        connectJDBC.executeUpdate(query, bookmark.getUserId(), bookmark.getIsbn13());
    }

    @Override
    public void removeAll() {
        String query = "DELETE FROM " + db_table;
        connectJDBC.executeUpdate(query);
    }
}
