package org.uet.library_management.ui.admin;

import org.uet.library_management.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.GridPane;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.services.documents.BookService;
import org.uet.library_management.tools.Mediator;

import java.util.List;

/**
 * Controller class for managing the editing interface of books.
 * This class uses JavaFX Pagination to display books in a paginated view.
 */
public class BookPageController {
    @FXML
    private Pagination pagination;

    private BookService service;
    private static final int PAGE_SIZE = 6;

//    private static final Map<Integer, List<Book>> booksCache = new LinkedHashMap<>();

    /**
     * Initializes the BookPageController by setting up the BookService, configuring the pagination,
     * and generating the initial page content.
     *
     * This method is automatically called when the FXML layout for this controller is loaded.
     * It performs the following actions:
     * 1. Initializes the BookService instance to facilitate operations on books.
     * 2. Sets the total number of pages in the pagination control based on the total number of books.
     * 3. Configures the pagination control to generate pages using a provided page factory method.
     */
    @FXML
    private void initialize() {
        service = new BookService();

        pagination.setPageCount(calculatePageCount());
        pagination.setPageFactory(this::createPage);
    }

    /**
     * Calculates the total number of pages required for pagination.
     *
     * This method uses the total number of entities retrieved from the service
     * and divides it by a constant PAGE_SIZE value to determine the number of pages.
     *
     * @return the total number of pages as an integer
     */
    private int calculatePageCount() {
        int totalEntites = service.countAll();
        return (int) Math.ceil((double) totalEntites / PAGE_SIZE);
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
//        pageGrid.setStyle("-fx-border-color: red; -fx-border-width: 2;");

        reloadPage(pageIndex, pageGrid);
        return pageGrid;
    }

    /**
     * Reloads the specified page with book data, populating it into the provided GridPane.
     * The page will display the title, authors, ISBN, description, and categories for each book.
     * Additionally, each book will have Edit and Remove buttons to perform actions on the book.
     *
     * @param pageIndex the index of the page to reload
     * @param pageGrid the GridPane where the book data will be displayed
     */
    private void reloadPage(int pageIndex, GridPane pageGrid) {
//        if (!booksCache.containsKey(pageIndex)) {
//            booksCache.put(pageIndex, service.findAllByPage(pageIndex + 1, PAGE_SIZE));
//        }
//        List<Book> books = booksCache.get(pageIndex);
        List<Book> books = service.findAllByPage(pageIndex + 1, PAGE_SIZE);

        Button createButton = new Button("Create");
        createButton.setOnAction(event -> handleCreate());
        pageGrid.add(createButton, 1, 0);
        addLabelToGridPane(pageGrid, "Title", 2, 0);
        addLabelToGridPane(pageGrid, "Authors", 3, 0);
        addLabelToGridPane(pageGrid, "isbn13", 4, 0);
        addLabelToGridPane(pageGrid, "Description", 5, 0);
        addLabelToGridPane(pageGrid, "Categories", 6, 0);

        int rowIndex = 1;
        for (Book book : books) {
            addLabelToGridPane(pageGrid, book.getTitle(), 2, rowIndex);
            addLabelToGridPane(pageGrid, book.getAuthors(), 3, rowIndex);
            addLabelToGridPane(pageGrid, book.getIsbn13(), 4, rowIndex);
            addLabelToGridPane(pageGrid, book.getDescription(), 5, rowIndex);
            addLabelToGridPane(pageGrid, book.getCategories(), 6, rowIndex);

            Button editButton = new Button("Edit");
            editButton.getStyleClass().add("edit-button");
            editButton.setOnAction(event -> openBookForm(book));
            pageGrid.add(editButton, 1, rowIndex);

            Button removeButton = new Button("Remove");
            removeButton.getStyleClass().add("remove-button");
            removeButton.setOnAction(event -> handleRemove(book));
            pageGrid.add(removeButton, 8, rowIndex);

            rowIndex++;
        }
    }

    /**
     * Adds a label with specified text to a given GridPane at the specified column and row indices.
     * The width of the label is set based on the column index.
     *
     * @param pageGrid The GridPane to which the label will be added.
     * @param text The text to be displayed in the label.
     * @param columnIndex The column index where the label will be placed.
     * @param rowIndex The row index where the label will be placed.
     */
    private void addLabelToGridPane(GridPane pageGrid, String text, int columnIndex, int rowIndex) {
        Label label = new Label(text);
        switch (columnIndex) {
            case 2:
                label.setPrefWidth(80);
                break;
            case 3, 4:
                label.setPrefWidth(30);
                break;
            case 5:
                label.setPrefWidth(240);
                break;
            case 6:
                label.setPrefWidth(100);
                break;
        }
        label.setPrefHeight(38);
        pageGrid.add(label, columnIndex, rowIndex);
    }

    /**
     * Opens the book form for the specified book.
     *
     * This method sets the specified book instance in the Mediator
     * and then changes the scene to the book form view.
     *
     * @param book the book instance to be managed in the form
     */
    private void openBookForm(Book book) {
        Mediator.book = book;
        SceneManager.getInstance().setSubScene("admin/forms/bookForm.fxml");
    }

    /**
     * Handles the action of creating a new book entry.
     *
     * This method is triggered by the associated FXML layout and
     * opens the book form for creating a new book.
     */
    @FXML
    private void handleCreate() {
        openBookForm(null);
    }

    /**
     * Handles the removal of a book from the book collection, updates the pagination control,
     * and adjusts the current page index if necessary.
     *
     * @param book the Book object to be removed from the collection
     */
    private void handleRemove(Book book) {
        int currentPageIndex = pagination.getCurrentPageIndex();
        service.remove(book);

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
     *
     * This method sets the current page index of the pagination control to 0,
     * effectively navigating to the first page of the paginated content.
     */
    @FXML
    private void moveToFirstPage()
    {
        pagination.setCurrentPageIndex(0);
    }

    /**
     * Moves the pagination control to the last page.
     *
     * This method sets the current page index of the pagination control
     * to the last page, effectively navigating to the end of the paginated content.
     */
    @FXML
    private void moveToLastPage()
    {
        pagination.setCurrentPageIndex(calculatePageCount()-1);
    }

    /**
     * Skips the pagination to the next multiple of 10th page.
     *
     * This method calculates the target page index by determining the next multiple of ten
     * page relative to the current page index and sets the pagination control to that page.
     * If the target page index exceeds the total number of pages, it adjusts to the last available page.
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
     * Skips the pagination control backward by a calculated number of pages.
     *
     * This method determines the number of pages to skip by adding 10 to the
     * remainder of the current page index divided by 10. It then subtracts
     * this value from the current page index to get the target page index.
     * If the resulting target page index is less than zero, it defaults to zero.
     * The method updates the pagination control to the calculated target page index.
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
