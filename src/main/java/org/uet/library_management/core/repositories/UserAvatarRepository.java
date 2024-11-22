package org.uet.library_management.core.repositories;

import javafx.scene.image.Image;
import lombok.SneakyThrows;
import org.uet.library_management.ConnectJDBC;
import org.uet.library_management.core.entities.User;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * UserAvatarRepository is responsible for managing user avatars in the database.
 * It provides methods to find, add, update, and remove user avatars.
 */
public class UserAvatarRepository {
    private static final String PREFIX_ICONS = "/org/uet/library_management/icons/";

    private final String db_table;

    private final ConnectJDBC connectJDBC;

    /**
     * Initializes a new instance of the UserAvatarRepository class.
     * This constructor sets up the database connection and specifies the table used for storing user avatars.
     */
    public UserAvatarRepository() {
        connectJDBC = new ConnectJDBC();
        db_table = "user_avatars";
    }

    /**
     * Retrieves the avatar image associated with a given user ID from the database.
     *
     * @param userId the ID of the user whose avatar image is to be retrieved.
     * @return an Image object representing the user's avatar, or null if no avatar is found.
     * @throws RuntimeException if a database access error occurs.
     */
    public Image findByUserId(int userId) {
        Image image = null;
        String query = "SELECT avatar FROM " + db_table + " WHERE userId = ?";

        try (ResultSet rs = connectJDBC.executeQueryWithParams(query, userId)) {
            while (rs.next()) {
                Blob blob = rs.getBlob("avatar");
                byte[] imageBytes = blob.getBytes(1, (int) blob.length());

                // Convert byte array to JavaFX Image
                ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
                image = new Image(inputStream);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return image;
    }

    /**
     * Adds a new user avatar to the database.
     *
     * @param userId the ID of the user to associate with the avatar
     * @param avatar a byte array representing the user's avatar image
     * @throws RuntimeException if a database access error occurs
     */
    public void add(int userId, byte[] avatar) {
        String query = "INSERT INTO " + db_table + " (userId, avatar) VALUES (?, ?)";

        connectJDBC.executeUpdate(query, userId, avatar);
    }

    /**
     * Updates the avatar image for the specified user in the database.
     *
     * @param userId the ID of the user whose avatar is to be updated.
     * @param avatar a byte array representing the new avatar image.
     */
    public void update(int userId, byte[] avatar) {
        String query = "UPDATE " + db_table + " SET avatar = ? WHERE userId = ?";

        connectJDBC.executeUpdate(query, avatar, userId);
    }

    /**
     * Removes a user's avatar from the database based on the user ID.
     *
     * @param userId the ID of the user whose avatar is to be removed.
     */
    public void remove(int userId) {
        String query = "DELETE FROM " + db_table + " WHERE userId = ?";

        connectJDBC.executeUpdate(query, userId);
    }

    /**
     * Removes all user avatars from the database.
     *
     * This method deletes all records from the table specified in the db_table field,
     * effectively clearing all user avatars stored in the database.
     *
     * @throws RuntimeException if a database access error occurs
     */
    public void removeAll() {
        String query = "DELETE FROM " + db_table;

        connectJDBC.executeUpdate(query);
    }
}
