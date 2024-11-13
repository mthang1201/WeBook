package org.uet.library_management;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class ConnectJDBCTest {

    /**
     * ConnectJDBC is a class representing a DataBase connection
     * connect is a method used to establish a connection with the DataBase
     */

    @Test
    public void testConnect_throwsException() {
        try (MockedStatic<DriverManager> mockedDriverManager = Mockito.mockStatic(DriverManager.class)) {
            mockedDriverManager.when(() -> DriverManager.getConnection(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                    .thenThrow(SQLException.class);
            ConnectJDBC connectJDBC = new ConnectJDBC();
            assertThrows(RuntimeException.class, connectJDBC::connect, "connect should throw RuntimeException when getConnection fails");
        }
    }

    @Test
    public void testConnect_success() {
        try (MockedStatic<DriverManager> mockedDriverManager = Mockito.mockStatic(DriverManager.class)) {
            Connection mockConnection = mock(Connection.class);
            mockedDriverManager.when(() -> DriverManager.getConnection(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                    .thenReturn(mockConnection);
            ConnectJDBC connectJDBC = new ConnectJDBC();
            Connection connection = connectJDBC.connect();
            assertEquals(mockConnection, connection, "connect should return connection when getConnection succeeds");
        }
    }
}