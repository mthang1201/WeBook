package org.uet.library_management.ui.settings;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.uet.library_management.SceneManager;
import org.uet.library_management.core.repositories.UserAvatarRepository;
import org.uet.library_management.tools.AlertUtil;
import org.uet.library_management.tools.SessionManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

/**
 * The ChangeAvatarController class is responsible for managing the user interface and interactions related to changing a user's avatar.
 * It includes methods for initializing the controller, uploading an image, handling avatar changes,
 * and responding to button actions.
 */
public class ChangeAvatarController {
    @FXML
    public Button changeAvatarButton;

    @FXML
    private ImageView imageView;

    private Image image;

    private UserAvatarRepository repository;

    /**
     * Initializes the controller by setting up the repository for managing user avatars.
     * This method is automatically called after the FXML file has been loaded.
     * It sets the repository instance to a new UserAvatarRepository, which will be used
     * for database operations related to user avatars.
     */
    @FXML
    public void initialize() {
        repository = new UserAvatarRepository();
    }

    /**
     * Uploads an image file selected by the user and sets it as the new avatar.
     *
     * This method opens a file chooser dialog to allow the user to select an image file.
     * Supported image formats include PNG, JPG, JPEG, and GIF. Upon successful selection
     * and loading of the image, the image is displayed in the ImageView component and
     * the Change Avatar button is configured to trigger an avatar change.
     *
     * If the image file cannot be found or loaded, a FileNotFoundException is caught and its
     * stack trace is printed.
     */
    @FXML
    private void uploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        Stage stage = (Stage) imageView.getScene().getWindow();  // Get the current window
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            try {
                image = new Image(new FileInputStream(file));
                imageView.setImage(image);
                imageView.setFitWidth(40);
                imageView.setFitHeight(40);
                changeAvatarButton.setOnAction(event -> {
                    handleChangeAvatarButton(file);
                });
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Handles the action triggered when the 'Change Avatar' button is pressed.
     * This method updates the user's avatar image in the repository and reflects
     * the change in the session and UI.
     *
     * @param file the file containing the new avatar image to be set.
     */
    @SneakyThrows
    @FXML
    private void handleChangeAvatarButton(File file) {
        byte[] avatar = null;
        try {
            avatar = Files.readAllBytes(file.toPath());

            if (repository.findByUserId(SessionManager.user.getUserId()) == null) {
                repository.add(SessionManager.user.getUserId(), avatar);
            } else {
                repository.update(SessionManager.user.getUserId(), avatar);
            }

            SessionManager.currentAvatar.set(
                    repository.findByUserId(SessionManager.user.getUserId())
            );  // Triggers listener in ImageView

            AlertUtil.showWarningAlert(
                    "Thay Đổi Ảnh Đại Diện",
                    "Bạn đã thay đổi ảnh đại diện thành công.",
                    null,
                    null
            );

            SceneManager.getInstance().setSettingsScene("settings/settings.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles the action of the 'Back' button being pressed.
     *
     * <p>This method is triggered when the user presses the 'Back' button
     * in the ChangeAvatarController. It changes the scene to the settings
     * page by loading the specified FXML file.</p>
     */
    @SneakyThrows
    @FXML
    public void handleBackButton() {
        SceneManager.getInstance().setSettingsScene("settings/settings.fxml");
    }
}
