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
    @FXML private Label descriptionText;
    @FXML private Button borrowButton;
    @FXML private Button moreButton;

    @FXML private Label publisherName;
    @FXML private Label languageName;
    @FXML private Label publishedDateName;
    @FXML private Label isbn10Name;
    @FXML private Label isbn13Name;
    @FXML private Label pageCountName;
    @FXML private Label ratingCountName;
    @FXML private Label printTypeName;
    @FXML private Label maturityRatingsName;

    private boolean moreClicked = false;

    public void loadBookDetails(Book book) {
        title.setText(book.getTitle());
        author.setText(book.getAuthors());
        rates.setText(String.valueOf(book.getAverageRating()));
        categories.setText(book.getCategories());
        descriptionText.setText(book.getDescription());

        publisherName.setText(book.getPublisher());
        languageName.setText(book.getLanguage());
        publishedDateName.setText(book.getPublishedDate());
        isbn10Name.setText(book.getIsbn10());
        isbn13Name.setText(book.getIsbn13());
        pageCountName.setText(String.valueOf(book.getPageCount()));
        ratingCountName.setText(String.valueOf(book.getRatingsCount()));
        printTypeName.setText(book.getPrintType());
        maturityRatingsName.setText(book.getMaturityRating());

        if (book.getImageLinks().equals("null&fife=w800&format=webp") || book.getImageLinks().equals("https://via.placeholder.com/150")) {
            bookCover.setImage(getPlaceholder());
        } else {
            bookCover.setImage(new Image(book.getImageLinks()));
        }

        if (book.getDescription().length() > 300) {
            descriptionText.setText(book.getDescription().substring(0, 300) + "...");
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
        descriptionText.setText("N/A");

        publisherName.setText("N/A");
        languageName.setText("N/A");
        publishedDateName.setText("N/A");
        isbn10Name.setText("N/A");
        isbn13Name.setText("N/A");
        pageCountName.setText("N/A");
        ratingCountName.setText("N/A");
        printTypeName.setText("N/A");
        maturityRatingsName.setText("N/A");

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
