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

public class EditController {
    @FXML
    private Pagination pagination;

    private BookService bookService;
    private static final int PAGE_SIZE = 6;

//    private static final Map<Integer, List<Book>> booksCache = new LinkedHashMap<>();

    @FXML
    private void initialize() {
        bookService = new BookService();

        pagination.setPageCount(calculatePageCount());
        pagination.setPageFactory(this::createPage);
    }

    private int calculatePageCount() {
        int totalWords = bookService.countAll();
        return (int) Math.ceil((double) totalWords / PAGE_SIZE);
    }

    private GridPane createPage(int pageIndex) {
        GridPane pageGrid = new GridPane();
        pageGrid.setPrefWidth(700);
        pageGrid.setPrefHeight(280);
        pageGrid.setHgap(10);
//        pageGrid.setStyle("-fx-border-color: red; -fx-border-width: 2;");

        reloadPage(pageIndex, pageGrid);
        return pageGrid;
    }

    private void reloadPage(int pageIndex, GridPane pageGrid) {
//        if (!booksCache.containsKey(pageIndex)) {
//            booksCache.put(pageIndex, bookService.findAllByPage(pageIndex + 1, PAGE_SIZE));
//        }
//        List<Book> books = booksCache.get(pageIndex);
        List<Book> books = bookService.findAllByPage(pageIndex + 1, PAGE_SIZE);

        addLabelToGridPane(pageGrid, "Title", 1, 0);
        addLabelToGridPane(pageGrid, "Authors", 2, 0);
        addLabelToGridPane(pageGrid, "isbn13", 3, 0);
        addLabelToGridPane(pageGrid, "Description", 4, 0);
        addLabelToGridPane(pageGrid, "Categories", 5, 0);

        int rowIndex = 1;
        for (Book book : books) {
            addLabelToGridPane(pageGrid, book.getTitle(), 1, rowIndex);
            addLabelToGridPane(pageGrid, book.getAuthors(), 2, rowIndex);
            addLabelToGridPane(pageGrid, book.getIsbn13(), 3, rowIndex);
            addLabelToGridPane(pageGrid, book.getDescription(), 4, rowIndex);
            addLabelToGridPane(pageGrid, book.getCategories(), 5, rowIndex);

            Button editButton = new Button("Edit");
            editButton.getStyleClass().add("edit-button");
            editButton.setOnAction(event -> openBookForm(book));
            pageGrid.add(editButton, 6, rowIndex);

            Button removeButton = new Button("Remove");
            removeButton.getStyleClass().add("remove-button");
            removeButton.setOnAction(event -> handleRemove(book));
            pageGrid.add(removeButton, 7, rowIndex);

            rowIndex++;
        }
    }

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

    private void openBookForm(Book book) {
        Mediator.book = book;
        SceneManager.getInstance().setSubScene("admin/forms/bookForm.fxml");
    }

    @FXML
    private void handleCreate() {
        openBookForm(null);
    }

    private void handleRemove(Book book) {
        bookService.remove(book);
        pagination.setPageFactory(this::createPage);
    }

    @FXML
    private void moveToFirstPage()
    {
        pagination.setCurrentPageIndex(0);
    }

    @FXML
    private void moveToLastPage()
    {
        pagination.setCurrentPageIndex(calculatePageCount()-1);
    }

    @FXML
    private void skipNext()
    {
        int currentPageIndex = pagination.getCurrentPageIndex();
        int pageToSkip = 10-currentPageIndex%10;
        int targetPageIndex = Math.min(currentPageIndex+ pageToSkip, calculatePageCount()-1);
        pagination.setCurrentPageIndex(targetPageIndex);
    }

    @FXML
    private void skipPre()
    {
        int currentPageIndex = pagination.getCurrentPageIndex();
        int pageToSkip = 10+currentPageIndex%10;
        int targetPageIndex = Math.max(currentPageIndex - pageToSkip, 0);
        pagination.setCurrentPageIndex(targetPageIndex);
    }
}
