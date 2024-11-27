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
    private TextField loanDateField;
    @FXML private TextField dueDateField;
    @FXML private TextField returnDateField;
    @FXML private TextField statusField;
    @FXML private TextField isbn13Field;
    @FXML private TextField titleField;
    @FXML private TextField userIdField;

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
            loanDateField.setText(loan.getLoanDate());
            dueDateField.setText(loan.getDueDate());
            returnDateField.setText(loan.getReturnDate());
            statusField.setText(loan.getStatus());
            isbn13Field.setText(loan.getIsbn13());
            titleField.setText(loan.getTitle());
            userIdField.setText(String.valueOf(loan.getUserId()));
        }
    }

    /**
     * Checks if any of the required form fields (loanDate, dueDate, returnDate, status, isbn13, title, userId) are empty or null.
     *
     * @return true if any of the form fields are invalid (empty or null), false otherwise.
     */
    private boolean invalidField() {
        if (loanDateField.getText() == null || loanDateField.getText().trim().equals("")) { return true; }
        if (dueDateField.getText() == null || dueDateField.getText().trim().equals("")) { return true; }
        if (returnDateField.getText() == null || returnDateField.getText().trim().equals("")) { return true; }
        if (statusField.getText() == null || statusField.getText().trim().equals("")) { return true; }
        if (isbn13Field.getText() == null || isbn13Field.getText().trim().equals("")) { return true; }
        if (titleField.getText() == null || titleField.getText().trim().equals("")) { return true; }
        if (userIdField.getText() == null || userIdField.getText().trim().equals("")) { return true; }
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
        // Uncomment this if validation is necessary
        // if (invalidField()) { errorWarning.setText("Please fill out this form."); return; }

        Loan loan = new Loan();

        loan.setLoanDate(loanDateField.getText());
        loan.setDueDate(dueDateField.getText());
        loan.setReturnDate(returnDateField.getText());
        loan.setStatus(statusField.getText());
        loan.setIsbn13(isbn13Field.getText());
        loan.setTitle(titleField.getText());
        loan.setUserId(Integer.parseInt(userIdField.getText()));

        if (createMethod) {
            service.add(loan);
        } else {
            loan.setLoanId(Mediator.loan.getLoanId());
            service.update(loan);
        }

        returnToEdit();
    }
}
