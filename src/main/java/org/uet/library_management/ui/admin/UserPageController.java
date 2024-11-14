package org.uet.library_management.ui.admin;

import org.uet.library_management.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.GridPane;
import org.uet.library_management.core.entities.User;
import org.uet.library_management.core.services.UserService;
import org.uet.library_management.tools.Mediator;

import java.util.List;

/**
 * The UserPageController class is a JavaFX controller responsible for managing
 * the user list view, including pagination and CRUD operations on users.
 * It utilizes a Pagination control to handle paginated views of users retrieved from the UserService.
 */
public class UserPageController {
    @FXML
    private Pagination pagination;

    private UserService service;
    private static final int PAGE_SIZE = 6;

//    private static final Map<Integer, List<User>> usersCache = new LinkedHashMap<>();

    /**
     * Initializes the UserPageController by setting up the necessary services
     * and configuring the pagination control.
     *
     * This method is called automatically after the FXML elements have been loaded.
     * It initializes the UserService, sets the page count for the pagination control,
     * and sets the page factory to create new pages when needed.
     */
    @FXML
    private void initialize() {
        service = new UserService();

        pagination.setPageCount(calculatePageCount());
        pagination.setPageFactory(this::createPage);
    }

    /**
     * Calculates the total number of pages required for pagination based on the
     * total number of entities and a predefined page size.
     *
     * @return the total number of pages required
     */
    private int calculatePageCount() {
        int totalEntites = service.countAll();
        return (int) Math.ceil((double) totalEntites / PAGE_SIZE);
    }

    /**
     * Creates a new GridPane for the specified page index.
     *
     * This method initializes a GridPane with predefined width, height,
     * and horizontal gap settings. It then populates the GridPane with
     * user data by calling the reloadPage method.
     *
     * @param pageIndex the index of the page to create
     * @return a GridPane configured for the specified page index
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
     * Reloads the user information on the specified page and populates the GridPane with
     * user details and action buttons.
     *
     * @param pageIndex the index of the page to reload
     * @param pageGrid the GridPane to populate with user information
     */
    private void reloadPage(int pageIndex, GridPane pageGrid) {
//        if (!usersCache.containsKey(pageIndex)) {
//            usersCache.put(pageIndex, service.findAllByPage(pageIndex + 1, PAGE_SIZE));
//        }
//        List<User> users = usersCache.get(pageIndex);
        List<User> users = service.findAllByPage(pageIndex + 1, PAGE_SIZE);

        addLabelToGridPane(pageGrid, "Title", 1, 0);
        addLabelToGridPane(pageGrid, "Authors", 2, 0);
        addLabelToGridPane(pageGrid, "isbn13", 3, 0);
        addLabelToGridPane(pageGrid, "Description", 4, 0);
        addLabelToGridPane(pageGrid, "Categories", 5, 0);

        int rowIndex = 1;
        for (User user : users) {
            addLabelToGridPane(pageGrid, user.getName(), 1, rowIndex);
            addLabelToGridPane(pageGrid, user.getEmail(), 2, rowIndex);
            addLabelToGridPane(pageGrid, user.getAddress(), 3, rowIndex);
            addLabelToGridPane(pageGrid, user.getMembershipStatus(), 4, rowIndex);
            addLabelToGridPane(pageGrid, user.getPrivileges(), 5, rowIndex);

            Button editButton = new Button("Edit");
            editButton.getStyleClass().add("edit-button");
            editButton.setOnAction(event -> openUserForm(user));
            pageGrid.add(editButton, 6, rowIndex);

            Button removeButton = new Button("Remove");
            removeButton.getStyleClass().add("remove-button");
            removeButton.setOnAction(event -> handleRemove(user));
            pageGrid.add(removeButton, 7, rowIndex);

            rowIndex++;
        }
    }

    /**
     * Adds a label with specified text to a GridPane at given column and row indices.
     *
     * Depending on the columnIndex, this method sets different preferred widths
     * for the label to ensure that labels in certain columns have consistent appearance.
     *
     * @param pageGrid the GridPane to which the label will be added
     * @param text the text to be displayed in the label
     * @param columnIndex the column position within the GridPane where the label will be placed
     * @param rowIndex the row position within the GridPane where the label will be placed
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
     * Opens the user form and sets the user in the mediator.
     *
     * This method assigns the provided user to the static user field in the Mediator
     * class and sets the sub-scene to the user form using the SceneManager.
     *
     * @param user the user to be set in the mediator and to be managed in the form
     */
    private void openUserForm(User user) {
        Mediator.user = user;
        SceneManager.getInstance().setSubScene("admin/forms/userForm.fxml");
    }

    /**
     * Handles the creation of a new user.
     *
     * This method invokes the openUserForm method with a null parameter, which is used to open
     * the user form without pre-filling it with any user data. This is typically used
     * when creating a new user.
     */
    @FXML
    private void handleCreate() {
        openUserForm(null);
    }

    /**
     * Handles the removal of a user from the system and updates the pagination control accordingly.
     *
     * @param user the user to be removed.
     */
    private void handleRemove(User user) {
        int currentPageIndex = pagination.getCurrentPageIndex();
        service.remove(user);

        int newPageCount = calculatePageCount();

        if (currentPageIndex >= newPageCount) {
            currentPageIndex = Math.max(currentPageIndex - 1, 0);
        }

        pagination.setPageCount(newPageCount);
        pagination.setPageFactory(this::createPage);
        pagination.setCurrentPageIndex(currentPageIndex);
    }

    /**
     * Sets the current page index of the pagination control to the first page.
     *
     * This method updates the pagination control to display the first page
     * by setting the current page index to 0.
     */
    @FXML
    private void moveToFirstPage()
    {
        pagination.setCurrentPageIndex(0);
    }

    /**
     * Sets the current page of the pagination control to the last page.
     *
     * This method uses the calculatePageCount method to determine the
     * total number of pages required and sets the current page index
     * of the pagination control to the last page (index value: total pages - 1).
     */
    @FXML
    private void moveToLastPage()
    {
        pagination.setCurrentPageIndex(calculatePageCount()-1);
    }

    /**
     * Skips the pagination control to the next multiple of 10 pages.
     *
     * This method calculates the number of pages needed to skip to reach the next
     * multiple of 10 from the current page. The pagination is then updated to
     * the target page index, which is the lesser of the calculated target page
     * or the last page available.
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
     * Skips back pages in the pagination control to the previous multiple of 10.
     *
     * This method calculates the target page to skip to by subtracting a value,
     * which is the sum of 10 and the remainder of the current page index divided by 10, from the current page index.
     * The target page index is then set to the higher value of either this calculated target or 0.
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
