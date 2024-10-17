package org.uet.library_management.ui.search;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.uet.library_management.tools.Mediator;

public class SuggestSearchController {
    @FXML
    public Label onSearchLabel;

    @FXML
    public void initialize() {
        String searchText = Mediator.getInstance().getText();
        onSearchLabel.setText("Đang hiển thị gợi ý liên quan đến \"" + searchText + "\"");
    }
}
