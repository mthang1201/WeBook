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

/**
 * Controller class for managing the Loan Page in a JavaFX application.
 *
 * This class handles the pagination and display of loans on the Loan Page.
 * It interacts with the LoanService to fetch loan data and updates the page content accordingly.
 */
public class LoanPageController {
    @FXML
    private Pagination pagination;

    private LoanService service;
    private static final int PAGE_SIZE = 6;

//    private static final Map<Integer, List<Loan>> booksCache = new LinkedHashMap<>();

    /**
     * Initializes the LoanPageController.
     *
     * This method configures the pagination component by setting its page count
     * and page factory. It creates an instance of LoanService to handle the
     * business logic for loan management.
     *
     * The pagination's page count is determined by the total number of loan
     * records divided by the page size. The PageFactory is set to utilize
     * the createPage method.
     */
    @FXML
    private void initialize() {
        service = new LoanService();

        pagination.setPageCount(calculatePageCount());
        pagination.setPageFactory(this::createPage);
    }

    /**
     * Calculates the total number of pages required for pagination.
     *
     * This method uses the total number of loan records retrieved from the service
     * and divides it by the PAGE_SIZE to determine the number of pages needed.
     *
     * @return the total number of pages
     */
    private int calculatePageCount() {
        int totalEntites = service.countAll();
        return (int) Math.ceil((double) totalEntites / PAGE_SIZE);
    }

    /**
     * Creates a new page for pagination.
     *
     * This method sets up a GridPane with predefined dimensions and spacing,
     * and populates it with content for the specified page index using the reloadPage method.
     *
     * @param pageIndex the index of the page to be created
     * @return the GridPane containing the content for the specified page
     */
    private GridPane createPage(int pageIndex) {
        GridPane pageGrid = new GridPane();
        pageGrid.setPrefWidth(700);
        pageGrid.setPrefHeight(280);
        pageGrid.setHgap(10);
//        pageGrid.setStyle("-fx-border-color: red; -fx-border-width: 2;");

        reloadPage(pageIndex, pageGrid);
        return pageGrid;
    }

    /**
     * Reloads the content of the specified page with loan details.
     *
     * This method fetches loan data for the given page index using the service
     * and populates the provided GridPane with loan information such as loan date,
     * due date, status, ISBN, and user ID. It also adds "Edit" and "Remove" buttons
     * for each loan record to allow for editing and removal of loans.
     *
     * @param pageIndex the index of the page to be reloaded
     * @param pageGrid the GridPane to be populated with loan data
     */
    private void reloadPage(int pageIndex, GridPane pageGrid) {
//        if (!booksCache.containsKey(pageIndex)) {
//            booksCache.put(pageIndex, service.findAllByPage(pageIndex + 1, PAGE_SIZE));
//        }
//        List<Loan> books = booksCache.get(pageIndex);
        List<Loan> loans = service.findAllByPage(pageIndex + 1, PAGE_SIZE);

        addLabelToGridPane(pageGrid, "Title", 1, 0);
        addLabelToGridPane(pageGrid, "Authors", 2, 0);
        addLabelToGridPane(pageGrid, "isbn13", 3, 0);
        addLabelToGridPane(pageGrid, "Description", 4, 0);
        addLabelToGridPane(pageGrid, "Categories", 5, 0);

        int rowIndex = 1;
        for (Loan loan : loans) {
            addLabelToGridPane(pageGrid, loan.getLoanDate(), 1, rowIndex);
            addLabelToGridPane(pageGrid, loan.getDueDate(), 2, rowIndex);
            addLabelToGridPane(pageGrid, loan.getStatus(), 3, rowIndex);
            addLabelToGridPane(pageGrid, loan.getIsbn13(), 4, rowIndex);
            addLabelToGridPane(pageGrid, String.valueOf(loan.getUserId()), 5, rowIndex);

            Button editButton = new Button("Edit");
            editButton.getStyleClass().add("edit-button");
            editButton.setOnAction(event -> openLoanForm(loan));
            pageGrid.add(editButton, 6, rowIndex);

            Button removeButton = new Button("Remove");
            removeButton.getStyleClass().add("remove-button");
            removeButton.setOnAction(event -> handleRemove(loan));
            pageGrid.add(removeButton, 7, rowIndex);

            rowIndex++;
        }
    }

    /**
     * Adds a label to a specified GridPane at the given column and row indices.
     *
     * This method creates a new Label with the provided text and configures its width
     * based on the column index. The label is then added to the provided GridPane at
     * the specified column and row positions.
     *
     * @param pageGrid     the GridPane to which the label will be added
     * @param text         the text to be displayed inside the label
     * @param columnIndex  the column index where the label will be placed
     * @param rowIndex     the row index where the label will be placed
     */
    private void addLabelToGridPane(GridPane pageGrid, String text, int columnIndex, int rowIndex) {
        Label label = new Label(text);
        switch (columnIndex) {
            case 1:
                label.setPrefWidth(80);
                break;
            case 2, 3:
                label.setPrefWidth(30);
                break;
            case 4:
                label.setPrefWidth(240);
                break;
            case 5:
                label.setPrefWidth(100);
                break;
        }
        label.setPrefHeight(38);
        pageGrid.add(label, columnIndex, rowIndex);
    }

    /**
     * Opens the loan form by setting the current Loan instance in the Mediator and updating the sub-scene.
     *
     * @param loan the Loan instance to be displayed or edited in the loan form.
     */
    private void openLoanForm(Loan loan) {
        Mediator.loan = loan;
        SceneManager.getInstance().setSubScene("admin/forms/loanForm.fxml");
    }

    @FXML
    private void handleCreate() {
        openLoanForm(null);
    }

    /**
     * Handles the removal of a loan record and updates the pagination accordingly.
     *
     * This method removes the specified loan record using the service, recalculates
     * the total number of pages needed for pagination, and updates the pagination
     * controls to reflect the current state.
     *
     * @param loan the Loan object representing the loan to be removed
     */
    private void handleRemove(Loan loan) {
        int currentPageIndex = pagination.getCurrentPageIndex();
        service.remove(loan);

        int newPageCount = calculatePageCount();

        if (currentPageIndex >= newPageCount) {
            currentPageIndex = Math.max(currentPageIndex - 1, 0);
        }

        pagination.setPageCount(newPageCount);
        pagination.setPageFactory(this::createPage);
        pagination.setCurrentPageIndex(currentPageIndex);
    }

    /**
     * Moves to the first page in the pagination.
     *
     * This method sets the current page index of the pagination control
     * to zero, effectively displaying the first page of the paginated content.
     */
    @FXML
    private void moveToFirstPage()
    {
        pagination.setCurrentPageIndex(0);
    }

    /**
     * Moves to the last page in the pagination.
     *
     * This method sets the current page index of the pagination control
     * to the last page based on the total number of pages calculated by
     * the calculatePageCount method.
     */
    @FXML
    private void moveToLastPage()
    {
        pagination.setCurrentPageIndex(calculatePageCount()-1);
    }

    /**
     * Skips to the next major page in the pagination.
     *
     * This method calculates the next page index by determining the number
     * of pages to skip in order to align to the next major page increment (multiple of 10).
     * It ensures that the target page index does not exceed the total number of pages.
     * The current page index of the pagination component is then set to the target page index.
     */
    @FXML
    private void skipNext()
    {
        int currentPageIndex = pagination.getCurrentPageIndex();
        int pageToSkip = 10-currentPageIndex%10;
        int targetPageIndex = Math.min(currentPageIndex+ pageToSkip, calculatePageCount()-1);
        pagination.setCurrentPageIndex(targetPageIndex);
    }

    /**
     * Skips to the previous major page in the pagination.
     *
     * This method calculates the previous page index by determining the number
     * of pages to skip in order to align to the previous major page decrement (multiple of 10).
     * It ensures that the target page index does not go below zero. The current page index
     * of the pagination component is then set to the target page index.
     */
    @FXML
    private void skipPre()
    {
        int currentPageIndex = pagination.getCurrentPageIndex();
        int pageToSkip = 10+currentPageIndex%10;
        int targetPageIndex = Math.max(currentPageIndex - pageToSkip, 0);
        pagination.setCurrentPageIndex(targetPageIndex);
    }
}
