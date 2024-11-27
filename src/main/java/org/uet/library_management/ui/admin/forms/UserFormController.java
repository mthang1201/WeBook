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

    @FXML private TextField nameField;
    @FXML private TextField phoneNumberField;
    @FXML private TextField emailField;
    @FXML private TextField addressField;
    @FXML private TextField membershipStatusField;
    @FXML private TextField privilegesField;

    @FXML private Label errorWarning;

    private boolean createMethod = true;

    /**
     * Initializes the UserFormController by setting up necessary services and populating fields
     * with user data if available. This method is annotated with @FXML, indicating that
     * it is linked to the corresponding FXML UI file. It is typically run when the FXML
     * file is loaded.
     */
    @FXML
    private void initialize() {
        service = new UserService();

        User user = Mediator.user;
        if (user != null) {
            createMethod = false;
            nameField.setText(user.getName());
            phoneNumberField.setText(user.getPhoneNumber());
            emailField.setText(user.getEmail());
            addressField.setText(user.getAddress());
            membershipStatusField.setText(user.getMembershipStatus());
            privilegesField.setText(user.getPrivileges());
        }
    }

    /**
     * Checks if any of the user input fields are empty or contain only whitespace.
     *
     * @return true if any field is invalid (null or empty), false otherwise.
     */
    private boolean invalidField() {
        if (nameField.getText() == null || nameField.getText().trim().isEmpty()) { return true; }
        if (phoneNumberField.getText() == null || phoneNumberField.getText().trim().isEmpty()) { return true; }
        if (emailField.getText() == null || emailField.getText().trim().isEmpty()) { return true; }
        if (addressField.getText() == null || addressField.getText().trim().isEmpty()) { return true; }
        if (membershipStatusField.getText() == null || membershipStatusField.getText().trim().isEmpty()) { return true; }
        if (privilegesField.getText() == null || privilegesField.getText().trim().isEmpty()) { return true; }
        return false;
    }

    /**
     * Navigates the user back to the 'Edit User' page within the application's administrative interface.
     */
    @FXML
    private void returnToEdit() {
        SceneManager.getInstance().setSubScene("admin/userPage.fxml");
    }

    /**
     * Handles the action of saving a form by creating a new User object or updating an existing one.
     */
    @FXML
    private void handleSaveForm() {
        if (invalidField()) {
            errorWarning.setText("Please fill out this form.");
            return;
        }

        User user = new User();

        user.setName(nameField.getText());
        user.setPhoneNumber(phoneNumberField.getText());
        user.setEmail(emailField.getText());
        user.setAddress(addressField.getText());
        user.setMembershipStatus(membershipStatusField.getText());
        user.setPrivileges(privilegesField.getText());

        if (createMethod) {
            service.add(user);
        } else {
            user.setUserId(Mediator.user.getUserId());
            service.updateForm(user);
        }

        returnToEdit();
    }
}