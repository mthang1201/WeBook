package org.uet.library_management.ui.admin.forms;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.uet.library_management.SceneManager;
import org.uet.library_management.core.entities.Loan;
import org.uet.library_management.core.services.LoanService;
import org.uet.library_management.tools.Mediator;

/**
 * The LoanFormController class handles the logic for the loan form view in the application.
 * It manages the interaction between the form fields and the LoanService to create or update loan records.
 */
public class LoanFormController {
    private LoanService service;

    @FXML
    private TextField titleField;
    @FXML private TextField authorsField;
    @FXML private TextField isbn13Field;
    @FXML private TextField descriptionField;
    @FXML private TextField categoriesField;

    @FXML private Label errorWarning;

    private boolean createMethod = true;

    /**
     * Initializes the form with data if a Loan object is available from the Mediator.
     *
     * This method sets up the LoanService and checks if there is an existing loan record
     * to be edited. If a loan exists, it fills in the form fields with the loan's data,
     * allowing the user to update the details.
     */
    @FXML
    private void initialize() {
        service = new LoanService();

        Loan loan = Mediator.loan;
        if (loan != null) {
            createMethod = false;
            titleField.setText(loan.getLoanDate());
            authorsField.setText(loan.getDueDate());
            isbn13Field.setText(loan.getStatus());
            descriptionField.setText(loan.getIsbn13());
            categoriesField.setText(String.valueOf(loan.getUserId()));
        }
    }

    /**
     * Checks if any of the required form fields (title, authors, ISBN-13, description, categories) are empty or null.
     *
     * @return true if any of the form fields are invalid (empty or null), false otherwise.
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
     * Returns the current sub-scene to the "admin/loanPage.fxml".
     *
     * This method uses the SceneManager singleton to set the sub-scene
     * of the application back to the loan page within the admin section.
     * It is typically invoked when needing to navigate back to the loan
     * edit view from another sub-scene.
     */
    @FXML
    private void returnToEdit() {
        SceneManager.getInstance().setSubScene("admin/loanPage.fxml");
    }

    /**
     * Handles the save action for the loan form.
     *
     * This method creates a new Loan object and populates it with data
     * from the form fields. Depending on the value of the `createMethod` flag,
     * it either adds a new loan record or updates an existing one via the LoanService.
     * Finally, it navigates back to the edit view.
     */
    @FXML
    private void handleSaveForm() {
//        if (invalidField()) { errorWarning.setText("Please fill out this form."); return; }

        Loan loan = new Loan();

        loan.setLoanDate(titleField.getText());
        loan.setDueDate(authorsField.getText());
        loan.setStatus(isbn13Field.getText());
        loan.setIsbn13(descriptionField.getText());
        loan.setUserId(Integer.parseInt(categoriesField.getText()));

        if (createMethod) {
            service.add(loan);
        }
        else {
            loan.setLoanId(Mediator.loan.getLoanId());
            service.update(loan);
        }

        returnToEdit();
    }
}

