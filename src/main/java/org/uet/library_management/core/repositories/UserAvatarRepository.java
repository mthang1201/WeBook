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

public class UserAvatarRepository {
    private static final String PREFIX_ICONS = "/org/uet/library_management/icons/";

    private final String db_table;

    private final ConnectJDBC connectJDBC;

    public UserAvatarRepository() {
        connectJDBC = new ConnectJDBC();
        db_table = "user_avatars";
    }

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

    public void add(int userId, byte[] avatar) {
        String query = "INSERT INTO " + db_table + " (userId, avatar) VALUES (?, ?)";
        connectJDBC.executeUpdate(query, userId, avatar);
    }

    public void update(int userId, byte[] avatar) {
        String query = "UPDATE " + db_table + " SET avatar = ? WHERE userId = ?";
        connectJDBC.executeUpdate(query, avatar, userId);
    }

    public void remove(int userId) {
        String query = "DELETE FROM " + db_table + " WHERE userId = ?";
        connectJDBC.executeUpdate(query, userId);
    }

    public void removeAll() {
        String query = "DELETE FROM " + db_table;
        connectJDBC.executeUpdate(query);
    }
}
