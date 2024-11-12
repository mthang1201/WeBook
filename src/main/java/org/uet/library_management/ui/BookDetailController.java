package org.uet.library_management.ui;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.uet.library_management.SceneManager;
import org.uet.library_management.core.entities.DocumentEvaluation;
import org.uet.library_management.core.entities.Loan;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.services.DocumentEvaluationService;
import org.uet.library_management.core.services.LoanService;
import org.uet.library_management.core.services.documents.BookService;
import org.uet.library_management.tools.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    @FXML private Button postButton;
    @FXML private Button editReviewButton;

    @FXML private Label publisherName;
    @FXML private Label languageName;
    @FXML private Label publishedDateName;
    @FXML private Label isbn10Name;
    @FXML private Label isbn13Name;
    @FXML private Label pageCountName;
    @FXML private Label ratingCountName;
    @FXML private Label printTypeName;
    @FXML private Label maturityRatingsName;
    //@FXML private ListView<String> commentListView;
    @FXML private Label reviewRate;
    @FXML private TextArea reviewArea;
    @FXML private ToggleButton star1;
    @FXML private ToggleButton star2;
    @FXML private ToggleButton star3;
    @FXML private ToggleButton star4;
    @FXML private ToggleButton star5;
    @FXML private VBox RateAndReviewBox;
    @FXML private VBox existingReviewBox;

    private boolean hasEvaluated;
    @FXML private VBox reviewBox;


    private boolean moreClicked = false;
    private boolean isBorrowed = false;
    private int stars;

    private DocumentEvaluationService evaluationService = new DocumentEvaluationService();

    //private ObservableList<String> comments;

    public void loadBookDetails(Book book) {
        title.setText(book.getTitle());
        author.setText(book.getAuthors());
        updateRatings();
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
        LayoutUtils.setVboxMargin(RateAndReviewBox, 0, 0 ,0, 10);
        LayoutUtils.setButtonMargin(editReviewButton, 0, 0, 0 ,10);

        loadBookDetails(Mediator.bookDetail);
        List<ImageView> starImageViews = LayoutUtils.createListImageViews(
                ImageLoaderUtil.getStarImages(0), 50, 50, true);

        star1.setGraphic(starImageViews.get(0));
        star2.setGraphic(starImageViews.get(1));
        star3.setGraphic(starImageViews.get(2));
        star4.setGraphic(starImageViews.get(3));
        star5.setGraphic(starImageViews.get(4));

        checkReviewed();
        displayReviews();

        checkBorrowed();
        addHoverEffect(borrowButton);
        addHoverEffect(returnButton);
    }

    @FXML
    private void onBackButtonClicked() {
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
    private void onReturnButtonClicked() {
        handleReturnButton();
    }

    @FXML
    private void on1starButtonClicked(ActionEvent event) {
        handleRating(1);
    }

    @FXML
    private void on2starButtonClicked(ActionEvent event) {
        handleRating(2);
    }

    @FXML
    private void on3starButtonClicked(ActionEvent event) {
        handleRating(3);
    }

    @FXML
    private void on4starButtonClicked(ActionEvent event) {
        handleRating(4);
    }

    @FXML
    private void on5starButtonClicked(ActionEvent event) {
        handleRating(5);
    }

    @FXML
    private void onPostCommentClicked(ActionEvent event) {
        handlePostCommentButton(stars);
    }

    @FXML
    private void onEditReviewClicked(ActionEvent event) {
        handleEditReviewButton();
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
    private void handleRating(int stars) {
        this.stars = stars;
        List<ImageView> starImageViews = LayoutUtils.createListImageViews(
                ImageLoaderUtil.getStarImages(stars),
                50, 50, true);

        star1.setGraphic(starImageViews.get(0));
        star2.setGraphic(starImageViews.get(1));
        star3.setGraphic(starImageViews.get(2));
        star4.setGraphic(starImageViews.get(3));
        star5.setGraphic(starImageViews.get(4));
        reviewRate.setText("Ratings: " + stars + "/5");

    }

    private void handlePostCommentButton(int stars) {
        String comment = reviewArea.getText().trim();

        if (!comment.isEmpty()) {
            DocumentEvaluation docEvaluation = new DocumentEvaluation(
                    Mediator.bookDetail.getIsbn13(),
                    SessionManager.user.getUserId(),
                    stars,
                    comment,
                    new Timestamp(System.currentTimeMillis())
            );

            evaluationService.add(docEvaluation);

            AlertUtil.showInformationsDialog("Review Posted!",
                    null,
                    "Your review has been posted successfully.",
                    null);

            reviewArea.clear();
            displayReviews();
            checkReviewed();

            BookService bookService = new BookService();
            Mediator.bookDetail.setAverageRating(evaluationService.getAvgRatings(Mediator.bookDetail.getIsbn13()));
            bookService.update(Mediator.bookDetail);

            updateRatings();

        } else {
            AlertUtil.showErrorAlert("Empty Comment!",
                    null,
                    "Please write a comment before posting.",
                    null);
        }
    }

    private void handleEditReviewButton() {
        //onbuttonclicked -> hasEvaluated->false;
        //update
        DocumentEvaluation userEvaluation = evaluationService.getUserReview(
                Mediator.bookDetail.getIsbn13(),
                SessionManager.user.getUserId()
        );
        evaluationService.remove(userEvaluation);
        checkReviewed();
        displayReviews();
    }

    private void displayReviews() {
        reviewBox.getChildren().clear();

        List<DocumentEvaluation> reviews = evaluationService.findByIsbn13(Mediator.bookDetail.getIsbn13());
        if (reviews == null || reviews.isEmpty()) {
            Label noReviewLabel = new Label("No reviews yet.");
            noReviewLabel.setStyle("-fx-font-style: italic; -fx-text-fill: #777; -fx-padding: 0 0 0 10;");
            reviewBox.getChildren().add(noReviewLabel);
        } else {
            for (DocumentEvaluation review : reviews) {
                reviewBox.getChildren().add(LayoutUtils.createReviewBox(review));
            }
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



    private void checkBorrowed() {
        LoanService loanService = new LoanService();
        Loan loan = loanService.findById(SessionManager.user.getUserId(), Mediator.bookDetail.getIsbn13());
        if (loan == null) {
            isBorrowed = false;
        } else {
            isBorrowed = true;
        }
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
    }

    private void checkReviewed() {
        hasEvaluated = evaluationService.hasEvaluated(Mediator.bookDetail.getIsbn13(),
                SessionManager.user.getUserId());
        existingReviewBox.getChildren().clear();

        if (Mediator.bookDetail.getIsbn13() == null) {
            RateAndReviewBox.setVisible(false);
            RateAndReviewBox.setManaged(false);
            existingReviewBox.setVisible(false);
            existingReviewBox.setManaged(false);
            reviewBox.setVisible(true);
            reviewBox.setManaged(true);
            Label noReviewLabel = new Label("No reviews yet.");
            noReviewLabel.setStyle("-fx-font-style: italic; -fx-text-fill: #777; -fx-padding: 0 0 0 10;");
            reviewBox.getChildren().add(noReviewLabel);
            editReviewButton.setVisible(false);
            editReviewButton.setManaged(false);
            return;
        }

        if (hasEvaluated) {
            RateAndReviewBox.setVisible(false);
            RateAndReviewBox.setManaged(false);
            existingReviewBox.getChildren().add(LayoutUtils.createReviewBox(
                    evaluationService.getUserReview(Mediator.bookDetail.getIsbn13(), SessionManager.user.getUserId())
            ));
            existingReviewBox.setVisible(true);
            existingReviewBox.setManaged(true);
            editReviewButton.setVisible(true);
            editReviewButton.setManaged(true);

        } else {
            RateAndReviewBox.setVisible(true);
            RateAndReviewBox.setManaged(true);
            existingReviewBox.setVisible(false);
            existingReviewBox.setManaged(false);
            editReviewButton.setVisible(false);
            editReviewButton.setManaged(false);
        }
    }

    private void updateRatings() {
        if (evaluationService.getAvgRatings(Mediator.bookDetail.getIsbn13()) != 0.0) {
            rates.setText(String.valueOf(evaluationService.getAvgRatings(Mediator.bookDetail.getIsbn13())));
        } else {
            rates.setText(String.valueOf(Mediator.bookDetail.getAverageRating()));
        }
    }
}
