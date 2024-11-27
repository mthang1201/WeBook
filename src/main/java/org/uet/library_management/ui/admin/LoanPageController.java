package org.uet.library_management.ui.admin;

import org.uet.library_management.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.GridPane;

import org.uet.library_management.core.entities.Loan;
import org.uet.library_management.core.services.LoanService;
import org.uet.library_management.tools.Mediator;

import java.util.List;

public class LoanPageController {

    @FXML
    private Pagination pagination;

    private LoanService loanService;
    private static final int PAGE_SIZE = 6;

    /**
     * Initializes the LoanFormController by setting up the LoanService, configuring the pagination,
     * and generating the initial page content.
     *
     * This method is automatically called when the FXML layout for this controller is loaded.
     * It performs the following actions:
     * 1. Initializes the LoanService instance to facilitate operations on loans.
     * 2. Sets the total number of pages in the pagination control based on the total number of loans.
     * 3. Configures the pagination control to generate pages using a provided page factory method.
     */
    @FXML
    private void initialize() {
        loanService = new LoanService();

        pagination.setPageCount(calculatePageCount());
        pagination.setPageFactory(this::createPage);
    }

    /**
     * Calculates the total number of pages required for pagination.
     *
     * @return the total number of pages as an integer
     */
    private int calculatePageCount() {
        int totalEntities = loanService.countAll();
        return (int) Math.ceil((double) totalEntities / PAGE_SIZE);
    }

    /**
     * Creates a new page for the pagination control, configured as a GridPane.
     *
     * @param pageIndex the index of the page to be created
     * @return a GridPane configured with the specified page content
     */
    private GridPane createPage(int pageIndex) {
        GridPane pageGrid = new GridPane();
        pageGrid.setPrefWidth(700);
        pageGrid.setPrefHeight(280);
        pageGrid.setHgap(10);

        reloadPage(pageIndex, pageGrid);
        return pageGrid;
    }

    /**
     * Reloads the specified page with loan data, populating it into the provided GridPane.
     * The page will display the loan details such as book title, user, and loan dates.
     * Additionally, each loan will have Edit and Remove buttons to perform actions.
     *
     * @param pageIndex the index of the page to reload
     * @param pageGrid  the GridPane where the loan data will be displayed
     */
    private void reloadPage(int pageIndex, GridPane pageGrid) {
        List<Loan> loans = loanService.findAllByPage(pageIndex + 1, PAGE_SIZE);

        Button createButton = new Button("Create");
        createButton.setOnAction(event -> handleCreate());
        pageGrid.add(createButton, 1, 0);
        addLabelToGridPane(pageGrid, "Book Title", 2, 0);
        addLabelToGridPane(pageGrid, "User", 3, 0);
        addLabelToGridPane(pageGrid, "Loan Date", 4, 0);
        addLabelToGridPane(pageGrid, "Due Date", 5, 0);
        addLabelToGridPane(pageGrid, "Return Date", 6, 0);

        int rowIndex = 1;
        for (Loan loan : loans) {
            addLabelToGridPane(pageGrid, loan.getTitle(), 2, rowIndex);
            addLabelToGridPane(pageGrid, String.valueOf(loan.getUserId()), 3, rowIndex);
            addLabelToGridPane(pageGrid, loan.getLoanDate().toString(), 4, rowIndex);
            addLabelToGridPane(pageGrid, loan.getDueDate().toString(), 5, rowIndex);
            addLabelToGridPane(pageGrid, loan.getReturnDate() != null ? loan.getReturnDate().toString() : "Not Returned", 6, rowIndex);

            Button editButton = new Button("Edit");
            editButton.getStyleClass().add("edit-button");
            editButton.setOnAction(event -> openLoanForm(loan));
            pageGrid.add(editButton, 1, rowIndex);

            Button removeButton = new Button("Remove");
            removeButton.getStyleClass().add("remove-button");
            removeButton.setOnAction(event -> handleRemove(loan));
            pageGrid.add(removeButton, 8, rowIndex);

            rowIndex++;
        }
    }

    /**
     * Adds a label with specified text to a given GridPane at the specified column and row indices.
     *
     * @param pageGrid   The GridPane to which the label will be added.
     * @param text       The text to be displayed in the label.
     * @param columnIndex The column index where the label will be placed.
     * @param rowIndex    The row index where the label will be placed.
     */
    private void addLabelToGridPane(GridPane pageGrid, String text, int columnIndex, int rowIndex) {
        Label label = new Label(text);
        label.setPrefHeight(38);
        label.setPrefWidth(100);
        pageGrid.add(label, columnIndex, rowIndex);
    }

    /**
     * Opens the loan form for the specified loan.
     *
     * This method sets the specified loan instance in the Mediator
     * and then changes the scene to the loan form view.
     *
     * @param loan the loan instance to be managed in the form
     */
    private void openLoanForm(Loan loan) {
        Mediator.loan = loan;
        SceneManager.getInstance().setSubScene("admin/forms/loanForm.fxml");
    }

    /**
     * Handles the action of creating a new loan entry.
     *
     * This method is triggered by the associated FXML layout and
     * opens the loan form for creating a new loan.
     */
    @FXML
    private void handleCreate() {
        openLoanForm(null);
    }

    /**
     * Handles the removal of a loan from the loan collection, updates the pagination control,
     * and adjusts the current page index if necessary.
     *
     * @param loan the Loan object to be removed from the collection
     */
    private void handleRemove(Loan loan) {
        int currentPageIndex = pagination.getCurrentPageIndex();
        loanService.remove(loan);

        int newPageCount = calculatePageCount();

        if (currentPageIndex >= newPageCount) {
            currentPageIndex = Math.max(currentPageIndex - 1, 0);
        }

        pagination.setPageCount(newPageCount);
        pagination.setPageFactory(this::createPage);
        pagination.setCurrentPageIndex(currentPageIndex);
    }

    /**
     * Moves the pagination control to the first page.
     */
    @FXML
    private void moveToFirstPage() {
        pagination.setCurrentPageIndex(0);
    }

    /**
     * Moves the pagination control to the last page.
     */
    @FXML
    private void moveToLastPage() {
        pagination.setCurrentPageIndex(calculatePageCount() - 1);
    }

    /**
     * Skips the pagination to the next multiple of 10th page.
     */
    @FXML
    private void skipNext() {
        int currentPageIndex = pagination.getCurrentPageIndex();
        int pageToSkip = 10 - currentPageIndex % 10;
        int targetPageIndex = Math.min(currentPageIndex + pageToSkip, calculatePageCount() - 1);
        pagination.setCurrentPageIndex(targetPageIndex);
    }

    /**
     * Skips the pagination control backward by a calculated number of pages.
     */
    @FXML
    private void skipPre() {
        int currentPageIndex = pagination.getCurrentPageIndex();
        int pageToSkip = 10 + currentPageIndex % 10;
        int targetPageIndex = Math.max(currentPageIndex - pageToSkip, 0);
        pagination.setCurrentPageIndex(targetPageIndex);
    }
}
