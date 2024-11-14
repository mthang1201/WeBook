package org.uet.library_management.ui.admin;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;

/**
 * The DashboardController class is a JavaFX controller responsible for initializing
 * and managing the dashboard view components. This class sets up a ScrollPane containing
 * a FlowPane, and configures various properties of these UI elements.
 *
 * Fields:
 * - scrollpane: FXML-injected ScrollPane for the dashboard.
 * - flowPane: FXML-injected FlowPane to be managed within the ScrollPane.
 *
 * Methods:
 * - initialize: Called automatically to configure the ScrollPane and FlowPane after
 *   the FXML file has been loaded. Sets the content of the ScrollPane to the FlowPane,
 *   makes the ScrollPane fit its width to the content, enables panning for the ScrollPane,
 *   and adds padding to the FlowPane.
 */
public class DashboardController {
    @FXML
    public ScrollPane scrollpane;

    @FXML
    public FlowPane flowPane;

    /**
     * Initializes the dashboard view components after the FXML file has been loaded.
     * This method sets the content of the ScrollPane to the FlowPane, configures the
     * ScrollPane to fit its width to the content, enables panning for the ScrollPane,
     * and adds padding to the FlowPane.
     */
    @FXML
    public void initialize() {
        scrollpane.setContent(flowPane);
        scrollpane.setFitToWidth(true);
        scrollpane.setPannable(true);

        flowPane.setPadding(new Insets(10,10,10,10));


    }
}
