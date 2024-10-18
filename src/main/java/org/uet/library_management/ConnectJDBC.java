package org.uet.library_management;

import java.sql.*;

public class ConnectJDBC {
    private static final String ENDPOINT = "database-1.cfwaak8q6v1w.ap-southeast-2.rds.amazonaws.com";

    public Connection connect() {
        Connection conn;
        try {
//            String url = "jdbc:mysql://" + ENDPOINT + ":3306/library_db";
            String url = "jdbc:mysql://localhost:3306/library_db";
            String username = "root";
            String password = "root";
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    public ResultSet executeQuery(String query) {
        Statement statement;
        ResultSet rs;
        try {
            statement = connect().createStatement();
            rs = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    public ResultSet executeQueryWithParams(String query, Object... params) {
        PreparedStatement statement;
        ResultSet rs;
        try {
            statement = connect().prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                if(query.contains("LIKE ?"))
                {
                    statement.setObject(i + 1, params[i] + "%");
                }
                else statement.setObject(i + 1, params[i]);
            }

            rs = statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
    public void executeUpdate(String query, Object... params) {
        PreparedStatement statement;
        try {
            statement = connect().prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
