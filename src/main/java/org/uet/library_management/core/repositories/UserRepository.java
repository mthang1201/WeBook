package org.uet.library_management.core.repositories;

import org.uet.library_management.ConnectJDBC;
import org.uet.library_management.core.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements MySQLRepository<User> {
    private final String db_table;

    private final ConnectJDBC connectJDBC;

    public UserRepository() {
        connectJDBC = new ConnectJDBC();
        db_table = "users";
    }

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

    @Override
    public void add(User user) {
        String query = "INSERT INTO " + db_table + " (name, phoneNumber, email, address, membershipStatus, privileges, passwordHash) VALUES (?, ?, ?, ?, ?, ?, ?)";
        connectJDBC.executeUpdate(query, user.getName(), user.getPhoneNumber(), user.getEmail(),
                user.getAddress(), user.getMembershipStatus(), user.getPrivileges(),
                user.getPasswordHash());
    }

    @Override
    public void update(User user) {
        String query = "UPDATE " + db_table + " SET name = ?, phoneNumber = ?, email = ?, address = ?, membershipStatus = ?, privileges = ?, passwordHash = ? WHERE userId = ?";
        connectJDBC.executeUpdate(query, user.getName(), user.getPhoneNumber(), user.getEmail(),
                user.getAddress(), user.getMembershipStatus(), user.getPrivileges(),
                user.getPasswordHash(), user.getUserId());
    }

    @Override
    public void remove(User user) {
        String query = "DELETE FROM " + db_table + " WHERE userId = ?";
        connectJDBC.executeUpdate(query, user.getUserId());
    }

    @Override
    public void removeAll() {
        String query = "DELETE FROM " + db_table;
        connectJDBC.executeUpdate(query);
    }
}
