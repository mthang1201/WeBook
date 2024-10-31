package org.uet.library_management.ui.admin.forms;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.uet.library_management.SceneManager;
import org.uet.library_management.core.entities.User;
import org.uet.library_management.core.services.UserService;
import org.uet.library_management.tools.Mediator;

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

    private boolean invalidField() {
        if (titleField.getText() == null || titleField.getText().trim().equals("")) { return true; }
        if (authorsField.getText() == null || authorsField.getText().trim().equals("")) { return true; }
        if (isbn13Field.getText() == null || isbn13Field.getText().trim().equals("")) { return true; }
        if (descriptionField.getText() == null || descriptionField.getText().trim().equals("")) { return true; }
        if (categoriesField.getText() == null || categoriesField.getText().trim().equals("")) { return true; }
        return false;
    }

    @FXML
    private void returnToEdit() {
        SceneManager.getInstance().setSubScene("admin/userPage.fxml");
    }

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

