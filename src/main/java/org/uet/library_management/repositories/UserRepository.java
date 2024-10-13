package org.uet.library_management.repositories;

import org.uet.library_management.ConnectJDBC;
import org.uet.library_management.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements MySQLRepository<User> {
    private String db_table;

    private final ConnectJDBC connectJDBC;

    public UserRepository() {
        connectJDBC = new ConnectJDBC();
        db_table = "users";
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        String query = "SELECT * FROM " + db_table;
        ResultSet rs = connectJDBC.executeQuery(query);
        while (true) {
            try {
                if (!rs.next()) break;
                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setName(rs.getString("name"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setMembershipStatus(rs.getString("membershipStatus"));
                user.setPrivileges(rs.getString("privileges"));

                users.add(user);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return users;
    }

    @Override
    public List<User> findAllByPage(int page, int pageSize) {
        List<User> users = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        String query = "SELECT * FROM " + db_table + " LIMIT " + pageSize + " OFFSET " + offset;
        ResultSet rs = connectJDBC.executeQuery(query);
        while (true) {
            try {
                if (!rs.next()) break;
                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setName(rs.getString("name"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setMembershipStatus(rs.getString("membershipStatus"));
                user.setPrivileges(rs.getString("privileges"));

                users.add(user);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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
        ResultSet rs = connectJDBC.executeQueryWithParams(query, name);
        while (true) {
            try {
                if (!rs.next()) break;
                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setName(rs.getString("name"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setMembershipStatus(rs.getString("membershipStatus"));
                user.setPrivileges(rs.getString("privileges"));

                users.add(user);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return users;
    }

    @Override
    public void add(User user) {
        String query = "INSERT INTO " + db_table + " (name, phoneNumber, email, address, membershipStatus, privileges) VALUES (?, ?, ?, ?, ?, ?)";
        connectJDBC.executeUpdate(query, user.getName(), user.getPhoneNumber(), user.getEmail(), user.getAddress(), user.getMembershipStatus(), user.getPrivileges());
    }

    @Override
    public void update(User user) {
        String query = "UPDATE " + db_table + " SET name = ?, phoneNumber = ?, email = ?, address = ?, membershipStatus = ?, privileges = ? WHERE userId = ?";
        connectJDBC.executeUpdate(query, user.getName(), user.getPhoneNumber(), user.getEmail(), user.getAddress(), user.getMembershipStatus(), user.getPrivileges(), user.getUserId());
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
