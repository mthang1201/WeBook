package org.uet.library_management.ui;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.uet.library_management.SceneManager;
import org.uet.library_management.core.entities.Loan;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.services.LoanService;
import org.uet.library_management.tools.Mediator;
import org.uet.library_management.tools.SessionManager;

public class BookDetailController {

    @FXML private ImageView bookCover;
    @FXML private Label title;
    @FXML private Label author;
    @FXML private Label rates;
    @FXML private Label categories;
    @FXML private Label descriptionLabel;
    @FXML private Label descriptionText;
    @FXML private Button borrowButton;
    @FXML private Button moreButton;

    private boolean moreClicked = false;

    public void loadBookDetails(Book book) {
        title.setText(book.getTitle());
        author.setText(book.getAuthors());
        rates.setText(String.valueOf(book.getAverageRating()));
        categories.setText(book.getCategories());
        descriptionText.setText(book.getDescription());

        if (book.getImageLinks() != null && !book.getImageLinks().isEmpty()) {
            bookCover.setImage(new Image(book.getImageLinks()));
        } else {
            bookCover.setImage(getPlaceholder());
        }

        if (book.getDescription().length() > 150) {
            descriptionText.setText(book.getDescription().substring(0, 150) + "...");
            moreButton.setVisible(true);
        } else {
            moreButton.setVisible(false);
        }
    }

    @FXML
    private void initialize() {
        bookCover.setImage(getPlaceholder());
        title.setText("N/A");
        author.setText("N/A");
        rates.setText("N/A");
        categories.setText("N/A");
        descriptionLabel.setText("Description");
        descriptionText.setText("N/A");

        loadBookDetails(Mediator.bookDetail);
        addHoverEffect(borrowButton);
    }

    @FXML
    private void onBackButtonClicked(ActionEvent event) {
        SceneManager.getInstance().setSubScene("search/suggestSearch.fxml");
    }

    @FXML
    private void onBorrowButtonClicked(ActionEvent event) {
        handleBorrowButton();
    }

    @FXML
    private void onMoreButtonClicked(ActionEvent event) {
        if (!moreClicked) {
            descriptionText.setText(Mediator.bookDetail.getDescription());
            moreButton.setText("Less");
        } else {
            descriptionText.setText(Mediator.bookDetail.getDescription().substring(0, 150) + "...");
            moreButton.setText("...More");
        }
        moreClicked = !moreClicked;
    }

    private Image getPlaceholder() {
        String imageLinks = getClass().getResource("/org/uet/library_management/placeholder/165x249.png").toExternalForm();
        return new Image(imageLinks, true);
    }

    private void handleBorrowButton() {
        Book book = Mediator.bookDetail;
        Loan loan = new Loan(
                "2004-04-04",
                "2033-03-03",
                "2005-01-01",
                "returned",
                book.getIsbn13(),
                book.getTitle(),
                SessionManager.user.getUserId()
        );

        LoanService loanService = new LoanService();
        loanService.add(loan);
    }

    private void addHoverEffect(Button button) {
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(300), button);
        scaleUp.setToX(1.1);
        scaleUp.setToY(1.1);

        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(300), button);
        scaleDown.setToX(1.0);
        scaleDown.setToY(1.0);

        button.setOnMouseEntered(event -> scaleUp.playFromStart());
        button.setOnMouseExited(event -> scaleDown.playFromStart());
    }
}
