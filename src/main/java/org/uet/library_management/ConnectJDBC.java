package org.uet.library_management;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.*;

/**
 * The ConnectJDBC class provides methods to establish a connection with a MySQL database and execute
 * SQL queries and updates. It encapsulates the JDBC logic to connect to the database, execute queries,
 * and handle SQL exceptions.
 */
public class ConnectJDBC {
    private static final String ENDPOINT = "database-1.cfwaak8q6v1w.ap-southeast-2.rds.amazonaws.com";

    /**
     * Establishes a connection to the MySQL database using the JDBC driver.
     *
     * @return a Connection object representing the connection to the database
     * @throws RuntimeException if a database access error occurs
     */
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

    /**
     * Executes the given SQL query and returns the resulting ResultSet.
     *
     * @param query the SQL query to execute
     * @return the resulting ResultSet from executing the query
     * @throws RuntimeException if a database access error occurs
     */
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

    /**
     * Executes a SQL query with specified parameters and returns a ResultSet containing the results.
     *
     * @param query the SQL query to execute. It can include placeholders for parameters.
     * @param params the parameters to be set in the query's placeholders.
     * @return the resulting ResultSet from executing the query.
     * @throws RuntimeException if a database access error occurs.
     */
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

    /**
     * Executes a SQL update statement (such as INSERT, UPDATE, or DELETE) with the given parameters.
     *
     * @param query the SQL query to execute. It can include placeholders for parameters.
     * @param params the parameters to be set in the query's placeholders.
     * @throws RuntimeException if a database access error occurs.
     */
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
