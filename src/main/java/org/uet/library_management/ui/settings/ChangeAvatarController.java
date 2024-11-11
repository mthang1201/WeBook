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
import org.uet.library_management.ui.MenuController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

public class ChangeAvatarController {
    @FXML
    public Button changeAvatarButton;

    @FXML
    private ImageView imageView;

    private Image image;

    private UserAvatarRepository repository;

    @FXML
    public void initialize() {
        repository = new UserAvatarRepository();
    }

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
                    "Change avatar",
                    "You have changed your avatar successfully.",
                    null,
                    null
            );

            SceneManager.getInstance().setSettingsScene("settings/settings.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    @FXML
    public void handleBackButton() {
        SceneManager.getInstance().setSettingsScene("settings/settings.fxml");
    }
}
