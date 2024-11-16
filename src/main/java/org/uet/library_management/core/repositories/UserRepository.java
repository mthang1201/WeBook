package org.uet.library_management.core.repositories;

import org.uet.library_management.ConnectJDBC;
import org.uet.library_management.core.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * UserRepository is a concrete implementation of the MySQLRepository interface
 * providing CRUD operations for managing User entities in a MySQL database.
 */
public class UserRepository implements MySQLRepository<User> {
    private final String db_table;

    private final ConnectJDBC connectJDBC;

    /**
     * The UserRepository class is responsible for managing user data in the
     * database. It provides CRUD operations and uses the ConnectJDBC class
     * to connect to the database and execute SQL queries.
     */
    public UserRepository() {
        connectJDBC = new ConnectJDBC();
        db_table = "users";
    }

    public UserRepository(ConnectJDBC connectJDBC) {
        this.connectJDBC = connectJDBC;
        db_table = "users";
    }

    /**
     * Populates a User object with data retrieved from a ResultSet.
     *
     * @param rs the ResultSet object containing user data retrieved from the database.
     * @return a User object populated with data from the ResultSet.
     * @throws SQLException if any SQL error occurs while retrieving data from the ResultSet.
     */
    private User populateUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("userId"));
        user.setName(rs.getString("name"));
        user.setPhoneNumber(rs.getString("phoneNumber"));
        user.setEmail(rs.getString("email"));
        user.setAddress(rs.getString("address"));
        user.setMembershipStatus(rs.getString("membershipStatus"));
        user.setPrivileges(rs.getString("privileges"));
        user.setPasswordHash(rs.getString("passwordHash"));

        return user;
    }

    /**
     * Retrieves all users from the database.
     *
     * @return a list of all users retrieved from the database.
     * @throws RuntimeException if a database access error occurs.
     */
    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM " + db_table;

        try (ResultSet rs = connectJDBC.executeQuery(query)) {
            while (rs.next()) {
                users.add(populateUser(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    /**
     * Retrieves a paginated list of users from the database.
     *
     * @param page the page number to retrieve.
     * @param pageSize the number of users per page.
     * @return a list of users for the specified page.
     */
    @Override
    public List<User> findAllByPage(int page, int pageSize) {
        List<User> users = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        String query = "SELECT * FROM " + db_table + " LIMIT " + pageSize + " OFFSET " + offset;

        try (ResultSet rs = connectJDBC.executeQuery(query)) {
            while (rs.next()) {
                users.add(populateUser(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    /**
     * Counts the total number of records in the associated database table.
     *
     * @return the total count of records in the table
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
     * Retrieves a list of users whose names match the given name pattern.
     *
     * @param name the name pattern to match against user names in the database.
     * @return a list of users whose names match the provided pattern.
     */
    public List<User> findByName(String name) {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM " + db_table + " WHERE name LIKE ?";

        try (ResultSet rs = connectJDBC.executeQueryWithParams(query, name)) {
            while (rs.next()) {
                users.add(populateUser(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    /**
     * Finds a user by their email address.
     *
     * @param email the email address of the user to find.
     * @return an Optional containing the found User, or an empty Optional if no user was found.
     */
    public Optional<User> findByEmail(String email) {
        User user = null;
        String query = "SELECT * FROM " + db_table + " WHERE email = ?";

        try (ResultSet rs = connectJDBC.executeQueryWithParams(query, email)) {
            while (rs.next()) {
                user = populateUser(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(user);
    }

    /**
     * Adds a user to the database.
     *
     * @param user the User object containing data to be inserted into the database.
     */
    @Override
    public void add(User user) {
        String query = "INSERT INTO " + db_table + " (name, phoneNumber, email, address, membershipStatus, privileges, passwordHash) VALUES (?, ?, ?, ?, ?, ?, ?)";
        connectJDBC.executeUpdate(query, user.getName(), user.getPhoneNumber(), user.getEmail(),
                user.getAddress(), user.getMembershipStatus(), user.getPrivileges(),
                user.getPasswordHash());
    }

    /**
     * Updates the information of an existing user in the database.
     *
     * @param user the User object containing the updated information to be stored in the database.
     */
    @Override
    public void update(User user) {
        String query = "UPDATE " + db_table + " SET name = ?, phoneNumber = ?, email = ?, address = ?, membershipStatus = ?, privileges = ?, passwordHash = ? WHERE userId = ?";
        connectJDBC.executeUpdate(query, user.getName(), user.getPhoneNumber(), user.getEmail(),
                user.getAddress(), user.getMembershipStatus(), user.getPrivileges(),
                user.getPasswordHash(), user.getUserId());
    }

    /**
     * Removes a specified user from the database.
     *
     * @param user the User object to be removed from the database.
     */
    @Override
    public void remove(User user) {
        String query = "DELETE FROM " + db_table + " WHERE userId = ?";
        connectJDBC.executeUpdate(query, user.getUserId());
    }

    /**
     * Deletes all records from the associated database table.
     *
     * This method constructs a SQL DELETE statement to remove all
     * rows from the table specified by the `db_table` field and
     * executes the statement using the `connectJDBC` utility to
     * interact with the database.
     *
     * @throws RuntimeException if a database access error occurs
     *                          while executing the SQL statement.
     */
    @Override
    public void removeAll() {
        String query = "DELETE FROM " + db_table;
        connectJDBC.executeUpdate(query);
    }
}
