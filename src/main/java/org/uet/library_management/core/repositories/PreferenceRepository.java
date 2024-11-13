package org.uet.library_management.core.repositories;

import org.uet.library_management.ConnectJDBC;
import org.uet.library_management.core.entities.Preference;
import org.uet.library_management.core.entities.Preference;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The `PreferenceRepository` class provides methods to manipulate user preferences stored in a MySQL database.
 * It implements the `MySQLRepository` interface, providing concrete implementations for common database operations.
 */
public class PreferenceRepository implements MySQLRepository<Preference> {
    private final String db_table;

    private final ConnectJDBC connectJDBC;

    public PreferenceRepository() {
        connectJDBC = new ConnectJDBC();
        db_table = "preferences";
    }

    public void addPreferenceForUser(int userId, List<String> categoryNames) {
        for (String categoryName : categoryNames) {
            String queryCheck = "SELECT categoryCount FROM " + db_table + " WHERE userId = ? AND categoryName = ?";
            int count = 0;
            try (ResultSet rs = connectJDBC.executeQueryWithParams(queryCheck, userId, categoryName)) {
                if (rs.next()) {
                    count = rs.getInt("categoryCount");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if (count > 0) {
                // Increment existing category count
                String queryIncrement = "UPDATE " + db_table + " SET categoryCount = categoryCount + 1 WHERE userId = ? AND categoryName = ?";
                connectJDBC.executeUpdate(queryIncrement, userId, categoryName);
            } else {
                // Add new category with count initialized to 1
                String queryInsert = "INSERT INTO " + db_table + " (userId, categoryName, categoryCount) VALUES (?, ?, ?)";
                connectJDBC.executeUpdate(queryInsert, userId, categoryName, 1);
            }
        }
    }

    public int getCategoryCount(int userId, String categoryName) {
        int count = 0;
        String query = "SELECT categoryCount FROM " + db_table + " WHERE userId = ? AND categoryName = ?";

        try (ResultSet rs = connectJDBC.executeQueryWithParams(query, userId, categoryName)) {
            if (rs.next()) {
                count = rs.getInt("categoryCount");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return count;
    }

    public String getMaxCategory(int userId) {
        String maxCategory = null;
        String query = "SELECT categoryName FROM " + db_table + " WHERE userId = ? " +
                "ORDER BY categoryCount DESC LIMIT 1";

        try (ResultSet rs = connectJDBC.executeQueryWithParams(query, userId)) {
            if (rs.next()) {
                maxCategory = rs.getString("categoryName");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return maxCategory;
    }

    private Preference populatePreference(ResultSet rs) throws SQLException {
        Preference preference = new Preference();
        preference.setUserId(rs.getInt("userId"));
        preference.setCategoryName(rs.getString("categoryName"));
        preference.setCategoryCount(rs.getInt("categoryCount"));

        return preference;
    }

    @Override
    public List<Preference> findAll() {
        List<Preference> preferences = new ArrayList<>();
        String query = "SELECT * FROM " + db_table;

        try (ResultSet rs = connectJDBC.executeQuery(query)) {
            while (rs.next()) {
                preferences.add(populatePreference(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return preferences;
    }

    @Override
    public List<Preference> findAllByPage(int page, int pageSize) {
        List<Preference> preferences = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        String query = "SELECT * FROM " + db_table + " LIMIT " + pageSize + " OFFSET " + offset;

        try (ResultSet rs = connectJDBC.executeQuery(query)) {
            while (rs.next()) {
                preferences.add(populatePreference(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return preferences;
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

    public List<Preference> findByName(String name) {
        List<Preference> preferences = new ArrayList<>();
        String query = "SELECT * FROM " + db_table + " WHERE categoryName LIKE ?";

        try (ResultSet rs = connectJDBC.executeQueryWithParams(query, name)) {
            while (rs.next()) {
                preferences.add(populatePreference(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return preferences;
    }

    public Optional<Preference> findByUserId(int userId) {
        Preference preference = null;
        String query = "SELECT * FROM " + db_table + " WHERE userId = ?";

        try (ResultSet rs = connectJDBC.executeQueryWithParams(query, userId)) {
            while (rs.next()) {
                preference = populatePreference(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(preference);
    }

    @Override
    public void add(Preference preference) {
        String query = "INSERT INTO " + db_table + " (userId, categoryName, categoryCount) VALUES (?, ?, ?)";
        connectJDBC.executeUpdate(query, preference.getUserId(), preference.getCategoryName(),
                preference.getCategoryCount());
    }

    @Override
    public void update(Preference preference) {
        String query = "UPDATE " + db_table + " SET categoryName = ?, categoryCount = ? WHERE userId = ?";
        connectJDBC.executeUpdate(query, preference.getCategoryName(), preference.getCategoryCount(),
                preference.getUserId());
    }

    @Override
    public void remove(Preference preference) {
        String query = "DELETE FROM " + db_table + " WHERE userId = ? AND categoryName = ?";
        connectJDBC.executeUpdate(query, preference.getUserId(), preference.getCategoryName());
    }

    @Override
    public void removeAll() {
        String query = "DELETE FROM " + db_table;
        connectJDBC.executeUpdate(query);
    }
}
