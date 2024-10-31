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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UserPageController {
    @FXML
    private Pagination pagination;

    private UserService service;
    private static final int PAGE_SIZE = 6;

//    private static final Map<Integer, List<User>> usersCache = new LinkedHashMap<>();

    @FXML
    private void initialize() {
        service = new UserService();

        pagination.setPageCount(calculatePageCount());
        pagination.setPageFactory(this::createPage);
    }

    private int calculatePageCount() {
        int totalWords = service.countAll();
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

    private void openUserForm(User user) {
        Mediator.user = user;
        SceneManager.getInstance().setSubScene("admin/userForm.fxml");
    }

    @FXML
    private void handleCreate() {
        openUserForm(null);
    }

    private void handleRemove(User user) {
        service.remove(user);
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
