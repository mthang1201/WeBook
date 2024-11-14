package org.uet.library_management.ui.admin.forms;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.uet.library_management.SceneManager;
import org.uet.library_management.core.entities.User;
import org.uet.library_management.core.services.UserService;
import org.uet.library_management.tools.Mediator;

/**
 * The UserFormController class is responsible for handling the actions and interactions
 * for the user form UI within the application. This includes initializing the form with
 * user data (if available), validating the form fields, and handling the save action.
 */
public class UserFormController {
    private UserService service;

    @FXML
    private TextField titleField;
    @FXML private TextField authorsField;
    @FXML private TextField isbn13Field;
    @FXML private TextField descriptionField;
    @FXML private TextField categoriesField;

    @FXML private Label errorWarning;

    private boolean createMethod = true;

    /**
     * Initializes the UserFormController by setting up necessary services and populating fields
     * with user data if available. This method is annotated with @FXML, indicating that
     * it is linked to the corresponding FXML UI file. It is typically run when the FXML
     * file is loaded.
     *
     * This method performs the following steps:
     * - Initializes the UserService.
     * - Checks if a user exists in the Mediator.
     * - If a user exists, sets the createMethod flag to false.
     * - Populates the form fields (titleField, authorsField, isbn13Field, descriptionField, categoriesField)
     *   with the existing user's data.
     */
    @FXML
    private void initialize() {
        service = new UserService();

        User user = Mediator.user;
        if (user != null) {
            createMethod = false;
            titleField.setText(user.getName());
            authorsField.setText(user.getEmail());
            isbn13Field.setText(user.getAddress());
            descriptionField.setText(user.getMembershipStatus());
            categoriesField.setText(user.getPrivileges());
        }
    }

    /**
     * Checks if any of the user input fields (titleField, authorsField, isbn13Field,
     * descriptionField, categoriesField) are empty or contain only whitespace.
     *
     * @return true if any field is invalid (null or empty), false otherwise.
     */
    private boolean invalidField() {
        if (titleField.getText() == null || titleField.getText().trim().equals("")) { return true; }
        if (authorsField.getText() == null || authorsField.getText().trim().equals("")) { return true; }
        if (isbn13Field.getText() == null || isbn13Field.getText().trim().equals("")) { return true; }
        if (descriptionField.getText() == null || descriptionField.getText().trim().equals("")) { return true; }
        if (categoriesField.getText() == null || categoriesField.getText().trim().equals("")) { return true; }
        return false;
    }

    /**
     * Navigates the user back to the 'Edit User' page within the application's administrative interface.
     * This method is triggered by an FXML event and uses the SceneManager singleton to set the sub-scene to 'admin/userPage.fxml'.
     *
     * This function is typically called after a user form operation, such as saving new user details or
     * updating existing user information, to return the user to a view where they can continue editing user details.
     */
    @FXML
    private void returnToEdit() {
        SceneManager.getInstance().setSubScene("admin/userPage.fxml");
    }

    /**
     * Handles the action of saving a form by creating a new User object or updating an existing one.
     * This method retrieves text from various input fields, sets the corresponding properties
     * of a User object, and then either adds the new user or updates an existing user in the system.
     *
     * The method performs the following actions:
     * - Retrieves values from input fields and sets them to a User object.
     * - Checks the createMethod flag to determine whether to add a new user or update an existing user.
     * - Adds the user through the service if createMethod is true.
     * - Updates the existing user if createMethod is false, using the user ID from the Mediator.
     * - Calls the returnToEdit method to navigate back to the previous scene.
     */
    @FXML
    private void handleSaveForm() {
//        if (invalidField()) { errorWarning.setText("Please fill out this form."); return; }

        User user = new User();

        user.setName(titleField.getText());
        user.setEmail(authorsField.getText());
        user.setAddress(isbn13Field.getText());
        user.setMembershipStatus(descriptionField.getText());
        user.setPrivileges(categoriesField.getText());

        if (createMethod) {
            service.add(user);
        }
        else {
            user.setUserId(Mediator.user.getUserId());
            service.update(user);
        }

        returnToEdit();
    }
}

