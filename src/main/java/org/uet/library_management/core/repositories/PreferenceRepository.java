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

    /**
     * Constructs a new PreferenceRepository instance, initializes the JDBC connection,
     * and sets the database table name for user preferences.
     */
    public PreferenceRepository() {
        connectJDBC = new ConnectJDBC();
        db_table = "preferences";
    }

    /**
     * Adds preferences for a user by updating or inserting category counts.
     *
     * @param userId the ID of the user for whom the preferences are being added.
     * @param categoryNames a list of category names to be added or updated for the user.
     */
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

    /**
     * Retrieves the count of a specific category for a given user.
     *
     * @param userId the ID of the user whose category count is to be retrieved
     * @param categoryName the name of the category for which the count is to be retrieved
     * @return the count of the specified category for the given user
     */
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

    /**
     * Retrieves the category with the highest count for a given user.
     *
     * @param userId the ID of the user for whom the maximum category is to be retrieved
     * @return the name of the category with the highest count for the given user, or null if no category is found
     */
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

    /**
     * Populates a Preference object using the data from the given ResultSet.
     *
     * @param rs the ResultSet object containing data from the database.
     * @return a Preference object populated with data from the ResultSet.
     * @throws SQLException if an SQL error occurs while accessing the ResultSet.
     */
    private Preference populatePreference(ResultSet rs) throws SQLException {
        Preference preference = new Preference();
        preference.setUserId(rs.getInt("userId"));
        preference.setCategoryName(rs.getString("categoryName"));
        preference.setCategoryCount(rs.getInt("categoryCount"));

        return preference;
    }

    /**
     * Retrieves all user preferences from the database.
     * This method executes a SQL query to extract all records from the corresponding database table,
     * converts each result row into a Preference object, and collects these objects into a list.
     *
     * @return a list of all user preferences found in the database.
     */
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

    /**
     * Retrieves a paginated list of user preferences from the database.
     *
     * @param page the number of the page to retrieve
     * @param pageSize the number of preferences per page
     * @return a list of user preferences for the specified page
     */
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

    /**
     * Counts all records in the database table associated with user preferences.
     *
     * @return the total number of records in the user preferences table
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
     * Finds and retrieves a list of preferences that match the given name.
     *
     * @param name the category name to search for within the preferences.
     * @return a list of Preference objects that match the specified category name.
     */
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

    /**
     * Finds a user's preference by their user ID.
     *
     * @param userId the ID of the user whose preference is to be retrieved.
     * @return an Optional containing the user's preference if found, or an empty Optional if no preference is found.
     */
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

    /**
     * Adds a new user preference to the database.
     *
     * @param preference the Preference object containing the user ID, category name, and category count
     */
    @Override
    public void add(Preference preference) {
        String query = "INSERT INTO " + db_table + " (userId, categoryName, categoryCount) VALUES (?, ?, ?)";
        connectJDBC.executeUpdate(query, preference.getUserId(), preference.getCategoryName(),
                preference.getCategoryCount());
    }

    /**
     * Updates an existing user preference record in the database.
     *
     * @param preference the Preference object containing the updated user ID, category name, and category count.
     */
    @Override
    public void update(Preference preference) {
        String query = "UPDATE " + db_table + " SET categoryName = ?, categoryCount = ? WHERE userId = ?";
        connectJDBC.executeUpdate(query, preference.getCategoryName(), preference.getCategoryCount(),
                preference.getUserId());
    }

    /**
     * Removes a user preference from the database based on user ID and category name.
     *
     * @param preference the Preference object containing the user ID and category name to be removed.
     */
    @Override
    public void remove(Preference preference) {
        String query = "DELETE FROM " + db_table + " WHERE userId = ? AND categoryName = ?";
        connectJDBC.executeUpdate(query, preference.getUserId(), preference.getCategoryName());
    }

    /**
     * Removes all user preferences from the database table.
     * This method constructs and executes a DELETE SQL query
     * to remove all records from the user preferences table.
     */
    @Override
    public void removeAll() {
        String query = "DELETE FROM " + db_table;
        connectJDBC.executeUpdate(query);
    }
}
