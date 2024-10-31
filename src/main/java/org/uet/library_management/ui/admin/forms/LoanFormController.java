package org.uet.library_management.ui.admin.forms;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.uet.library_management.SceneManager;
import org.uet.library_management.core.entities.Loan;
import org.uet.library_management.core.services.LoanService;
import org.uet.library_management.tools.Mediator;

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

    @FXML
    private void initialize() {
        service = new LoanService();

        Loan loan = Mediator.loan;
        if (loan != null) {
            createMethod = false;
            titleField.setText(loan.getLoanDate());
            authorsField.setText(loan.getDueDate());
            isbn13Field.setText(loan.getStatus());
            descriptionField.setText(loan.getDocumentId());
            categoriesField.setText(loan.getUserId());
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
        SceneManager.getInstance().setSubScene("admin/loanPage.fxml");
    }

    @FXML
    private void handleSaveForm() {
//        if (invalidField()) { errorWarning.setText("Please fill out this form."); return; }

        Loan loan = new Loan();

        loan.setLoanDate(titleField.getText());
        loan.setDueDate(authorsField.getText());
        loan.setStatus(isbn13Field.getText());
        loan.setDocumentId(descriptionField.getText());
        loan.setUserId(categoriesField.getText());

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

