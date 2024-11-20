package org.uet.library_management.ui.settings;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import lombok.SneakyThrows;
import org.uet.library_management.SceneManager;
import org.uet.library_management.tools.ImageLoaderUtil;

/**
 * Controller class for handling events and actions in the "About" section of the application.
 *
 * This class is responsible for managing the interaction logic and UI transitions associated with the "About" section.
 */
public class AboutController {
    @FXML
    private ImageView logoImage;

    @SneakyThrows
    @FXML
    public void initialize() {
        logoImage.setImage(ImageLoaderUtil.getImage("title.png"));
        logoImage.setFitWidth(200);
        logoImage.setFitHeight(50);
        logoImage.setPreserveRatio(true);
    }

    /**
     * Handles the back button action in the "About" section, triggering a transition to the settings scene.
     *
     * This method is typically called when the user interacts with the back button in the "About"
     * section of the application. It uses the SceneManager singleton to switch the current scene
     * to the settings scene defined in the provided FXML file.
     */
    @SneakyThrows
    @FXML
    public void handleBackButton() {
        SceneManager.getInstance().setSettingsScene("settings/settings.fxml");
    }
}
