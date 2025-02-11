package org.uet.library_management.core.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.uet.library_management.ConnectJDBC;
import org.uet.library_management.core.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRepositoryTest {
    private UserRepository repository;

    @Mock
    private ConnectJDBC mockConnectJDBC;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        repository = new UserRepository(mockConnectJDBC);
    }

    @Test
    public void testFindAll() throws SQLException {
        // Mock behavior
        String query = "SELECT * FROM users";
        when(mockConnectJDBC.executeQuery(query)).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false); // Simulate 1 user
        when(mockResultSet.getInt("userId")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("John Doe");
        when(mockResultSet.getString("phoneNumber")).thenReturn("123456789");
        when(mockResultSet.getString("email")).thenReturn("john.doe@example.com");
        when(mockResultSet.getString("address")).thenReturn("123 Main St");
        when(mockResultSet.getString("membershipStatus")).thenReturn("Active");
        when(mockResultSet.getString("privileges")).thenReturn("Standard");
        when(mockResultSet.getString("passwordHash")).thenReturn("hashedPassword");

        // Execute
        List<User> users = repository.findAll();

        // Verify
        assertEquals(1, users.size());
        User user = users.get(0);
        assertEquals(1, user.getUserId());
        assertEquals("John Doe", user.getName());
        verify(mockConnectJDBC, times(1)).executeQuery(query);
    }

    @Test
    public void testFindByEmail() throws SQLException {
        // Mock behavior
        String query = "SELECT * FROM users WHERE email = ?";
        when(mockConnectJDBC.executeQueryWithParams(query, "john.doe@example.com")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("userId")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("John Doe");

        // Execute
        Optional<User> userOpt = repository.findByEmail("john.doe@example.com");

        // Verify
        assertTrue(userOpt.isPresent());
        assertEquals(1, userOpt.get().getUserId());
        verify(mockConnectJDBC, times(1)).executeQueryWithParams(query, "john.doe@example.com");
    }

    @Test
    public void testAdd() {
        // Mock behavior
        String query = "INSERT INTO users (name, phoneNumber, email, address, membershipStatus, privileges, passwordHash) VALUES (?, ?, ?, ?, ?, ?, ?)";
        User user = new User();
        user.setName("John Doe");
        user.setPhoneNumber("123456789");
        user.setEmail("john.doe@example.com");
        user.setAddress("123 Main St");
        user.setMembershipStatus("Active");
        user.setPrivileges("Standard");
        user.setPasswordHash("hashedPassword");

        // Execute
        doNothing().when(mockConnectJDBC).executeUpdate(anyString(), any());

        repository.add(user);

        // Verify
        verify(mockConnectJDBC, times(1)).executeUpdate(
                eq(query),
                eq("John Doe"),
                eq("123456789"),
                eq("john.doe@example.com"),
                eq("123 Main St"),
                eq("Active"),
                eq("Standard"),
                eq("hashedPassword")
        );
    }

    @Test
    public void testRemove() {
        // Mock behavior
        String query = "DELETE FROM users WHERE userId = ?";
        User user = new User();
        user.setUserId(1);

        // Execute
        doNothing().when(mockConnectJDBC).executeUpdate(anyString(), any());

        repository.remove(user);

        // Verify
        verify(mockConnectJDBC, times(1)).executeUpdate(query, 1);
    }

    @Test
    public void testCountAll() throws SQLException {
        // Mock behavior
        String query = "SELECT COUNT(*) AS total FROM users";
        when(mockConnectJDBC.executeQuery(query)).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("total")).thenReturn(5);

        // Execute
        int count = repository.countAll();

        // Verify
        assertEquals(5, count);
        verify(mockConnectJDBC, times(1)).executeQuery(query);
    }
}