package org.uet.library_management.ui;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.uet.library_management.SceneManager;
import org.uet.library_management.core.entities.Loan;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.services.LoanService;
import org.uet.library_management.tools.AlertUtil;
import org.uet.library_management.tools.ImageLoaderUtil;
import org.uet.library_management.tools.Mediator;
import org.uet.library_management.tools.SessionManager;

import java.time.LocalDate;

public class BookDetailController {

    @FXML private ImageView bookCover;
    @FXML private Label title;
    @FXML private Label author;
    @FXML private Label rates;
    @FXML private Label categories;
    @FXML private Label descriptionText;
    @FXML private Button borrowButton;
    @FXML private Button moreButton;
    @FXML private Button returnButton;
    @FXML public Button backButton;

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
    private boolean isBorrowed = false;

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
        bookCover.setImage(new Image(ImageLoaderUtil.resolveImageUrl(book.getImageLinks())));

        if (book.getDescription().length() > 300) {
            descriptionText.setText(book.getDescription().substring(0, 300) + "...");
            moreButton.setVisible(true);
        } else {
            moreButton.setVisible(false);
        }
    }

    @FXML
    private void initialize() {
        loadBookDetails(Mediator.bookDetail);
        isBorrowed = checkBorrowed();
        if (isBorrowed == true) {
            borrowButton.setDisable(true);
            isBorrowed = true;

            returnButton.setDisable(false);
            isBorrowed = true;
        } else {
            borrowButton.setDisable(false);

            returnButton.setDisable(true);
            isBorrowed = false;
        }
        addHoverEffect(borrowButton);
        addHoverEffect(returnButton);
    }

    @FXML
    private void onBackButtonClicked(ActionEvent event) {
//        SceneManager.getInstance().setSubScene("search/suggestSearch.fxml");
        SceneManager.getInstance().popSubScene();
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

    @FXML
    private void onReturnButtonClicked(ActionEvent event) {
        handleReturnButton();
    }

    private void handleBorrowButton() {
        if (isBorrowed == false) {
            DatePicker dueDatePicker = new DatePicker();
            dueDatePicker.setValue(LocalDate.now().plusWeeks(1));

            // Disable direct text input
            dueDatePicker.getEditor().setDisable(true);

            // Create a dialog for borrowing

            Alert dueDateDialog = AlertUtil.createInformationDialog(
                    "Select Due Date",
                    null,
                    "Please select a due date for borrowing the book:",
                    null
            );

            VBox dueDateContent = new VBox(10);
            dueDateContent.getChildren().add(dueDatePicker);
            dueDateDialog.getDialogPane().setContent(dueDateContent);

            dueDateDialog.showAndWait().ifPresent(response -> {
                LocalDate dueDate = dueDatePicker.getValue();
                if (response == ButtonType.OK) {
                    if (dueDate != null && dueDate.isBefore(LocalDate.now())) {
                        AlertUtil.showErrorAlert("Invalid Date!",
                                null,
                                "Please select a future date!",
                                null);
                        dueDatePicker.setValue(LocalDate.now().plusWeeks(1));
                    } else {
                        AlertUtil.showInformationsDialog("Success!",
                                null,
                                "Your changes have been saved successfully!",
                                null);
                        Book book = Mediator.bookDetail;
                        Loan loan = new Loan(
                                LocalDate.now().toString(),
                                dueDate.toString(),
                                null,
                                "borrowed",
                                book.getIsbn13(),
                                book.getTitle(),
                                SessionManager.user.getUserId()
                        );

                        LoanService loanService = new LoanService();
                        loanService.add(loan);

                        borrowButton.setDisable(true);
                        isBorrowed = true;

                        returnButton.setDisable(false);
                        isBorrowed = true;
                    }
                }
            });
        }
    }

    private void handleReturnButton() {
        if (isBorrowed == true) {
            LoanService loanService = new LoanService();
            Loan deleteLoan = loanService.findById(SessionManager.user.getUserId(), Mediator.bookDetail.getIsbn13());
            AlertUtil.showInformationsDialog(
                    "Success!",
                    null,
                    "You have returned " + deleteLoan.getTitle() + " successfully!",
                    null
            );
            loanService.remove(deleteLoan);

            borrowButton.setDisable(false);

            returnButton.setDisable(true);
            isBorrowed = false;
        }
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

    private boolean checkBorrowed() {
        LoanService loanService = new LoanService();
        Loan loan = loanService.findById(SessionManager.user.getUserId(), Mediator.bookDetail.getIsbn13());
        if (loan == null) {
            return false;
        } else {
            return true;
        }
    }
}
