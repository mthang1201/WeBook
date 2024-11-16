package org.uet.library_management.ui;

import javafx.animation.ScaleTransition;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.uet.library_management.SceneManager;
import org.uet.library_management.core.entities.Bookmark;
import org.uet.library_management.core.entities.DocumentEvaluation;
import org.uet.library_management.core.entities.Loan;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.core.services.BookmarkService;
import org.uet.library_management.core.services.DocumentEvaluationService;
import org.uet.library_management.core.services.LoanService;
import org.uet.library_management.core.services.documents.BookService;
import org.uet.library_management.tools.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

/**
 * The BookDetailController class is responsible for handling the UI interactions and
 * displaying details of a selected book in the graphical user interface.
 * It manages various elements such as the book cover, title, author, ratings,
 * and allows users to borrow the book, post reviews, and view more details.
 */
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
    @FXML private Button bookDetailBookmarkButton;
    @FXML private ToggleButton star1;
    @FXML private ToggleButton star2;
    @FXML private ToggleButton star3;
    @FXML private ToggleButton star4;
    @FXML private ToggleButton star5;
    @FXML private VBox RateAndReviewBox;
    @FXML private VBox existingReviewBox;
    @FXML private HBox titleAndBookmarkBox;

    private boolean hasEvaluated;
    @FXML private VBox reviewBox;


    private boolean moreClicked = false;
    private boolean isBorrowed = false;
    private boolean isMarked;
    private int stars;

    private DocumentEvaluationService evaluationService = new DocumentEvaluationService();

    //private ObservableList<String> comments;

    /**
     * Loads the details of the specified book into the UI components.
     *
     * @param book The book whose details are to be loaded. This includes title, authors, ratings,
     *             categories, description, publisher, language, published date, ISBN numbers,
     *             page count, ratings count, print type, maturity rating, and image links.
     */
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

        Task<Image> loadingBookCover = new Task<Image>() {
            @Override
            protected Image call() throws Exception {
                return new Image(ImageLoaderUtil.resolveImageUrl(book.getImageLinks()));
            }
        };
        loadingBookCover.setOnSucceeded(event -> {
            bookCover.setImage(loadingBookCover.getValue());
        });
        new Thread(loadingBookCover).start();

        if (book.getDescription().length() > 300) {
            descriptionText.setText(book.getDescription().substring(0, 300) + "...");
            moreButton.setVisible(true);
        } else {
            moreButton.setVisible(false);
        }
    }

    /**
     * Initializes the UI components of the BookDetailController.
     * <ol>
     * <li>Sets margin for the Rate and Review section and the edit review button using LayoutUtils.</li>
     * <li>Loads book details into the UI components from the Mediator class.</li>
     * <li>Loads star images for the rating system and sets them to star buttons.</li>
     * <li>Checks if the book has been reviewed and displays reviews accordingly.</li>
     * <li>Checks if the book has been borrowed and adds hover effects to the borrow and return buttons.</li>
     * </ol>
     */
    @FXML
    private void initialize() {
        LayoutUtils.setHBoxNodeMargin(bookDetailBookmarkButton, 0, 0, 0, 10);
        LayoutUtils.setVboxMargin(RateAndReviewBox, 0, 0 ,0, 10);
        LayoutUtils.setVBoxNodeMargin(editReviewButton, 0, 0, 0 ,10);

        loadBookDetails(Mediator.bookDetail);
        checkMarked();
        ImageView mark = LayoutUtils.createImageView(
                ImageLoaderUtil.getBookMarkImage(isMarked), 20, 20, true
        );
        List<ImageView> starImageViews = LayoutUtils.createListImageViews(
                ImageLoaderUtil.getStarImages(0), 50, 50, true);

        bookDetailBookmarkButton.setGraphic(mark);
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

    /**
     * Handles the action performed when the back button is clicked.
     * This method pops the current sub-scene from the scene stack managed by the SceneManager,
     * effectively navigating the user back to the previous sub-scene.
     */
    @FXML
    private void onBackButtonClicked() {
//        SceneManager.getInstance().setSubScene("search/suggestSearch.fxml");
        SceneManager.getInstance().popSubScene();
    }

    /**
     * Handles the event when the user clicks on the bookmark button for the book.
     * This method will check the ISBN of the book, and if the ISBN is valid,
     * it will either add or remove the bookmark based on the current state.
     *
     * If the ISBN of the book is invalid (null), an error message will be displayed.
     * After the bookmark operation is completed, a success message will be shown.
     */
    @FXML
    private void onBookmarkButtonClicked() {
        String isbn13 = Mediator.bookDetail.getIsbn13();
        if (isbn13 == null) {
            AlertUtil.showInformationsDialog(
                    "Lỗi",
                    null,
                    "ISBN của cuốn sách không hợp lệ. Vui lòng kiểm tra lại!",
                    null
            );
            return;
        }

        isMarked = !isMarked;
        ImageView mark = LayoutUtils.createImageView(
                ImageLoaderUtil.getBookMarkImage(isMarked), 20, 20, true
        );
        bookDetailBookmarkButton.setGraphic(mark);
        BookmarkService bookmarkService = new BookmarkService();

        if (isMarked == false) {
            bookmarkService.remove(new Bookmark(
                    SessionManager.user.getUserId(),
                    isbn13
            ));

            AlertUtil.showInformationsDialog(
                    "Thành công",
                    null,
                    "Bạn đã bỏ đánh dấu cuốn sách này thành công!",
                    null
            );
        } else {
            bookmarkService.add(new Bookmark(
                    SessionManager.user.getUserId(),
                    isbn13
            ));

            AlertUtil.showInformationsDialog(
                    "Thành công",
                    null,
                    "Bạn đã đánh dấu cuốn sách này thành công!",
                    null
            );
        }
    }


    /**
     * Handles the action event triggered when the borrow button is clicked.
     * This method invokes handleBorrowButton to process the borrowing of a book.
     *
     * @param event the event that triggered the borrow button click. It is an instance of ActionEvent,
     *              typically generated by user interaction with the borrow button.
     */
    @FXML
    private void onBorrowButtonClicked(ActionEvent event) {
        handleBorrowButton();
    }

    /**
     * Handles the action event triggered when the "More" button is clicked.
     * This method toggles the description text between full and truncated views
     * and updates the button text accordingly.
     *
     * @param event The event that triggered the "More" button click. It is an instance of ActionEvent,
     *              typically generated by user interaction with the button.
     */
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

    /**
     * Handles the action performed when the return button is clicked.
     * This method triggers the `handleReturnButton` function, which manages the
     * logic for returning a borrowed book.
     */
    @FXML
    private void onReturnButtonClicked() {
        handleReturnButton();
    }

    /**
     * Handles the action performed when the 1-star rating button is clicked.
     * This method invokes `handleRating` with a parameter value of 1,
     * updating the UI to reflect the 1-star rating selection.
     */
    @FXML
    private void on1starButtonClicked() {
        handleRating(1);
    }

    /**
     * Handles the action performed when the 2-star rating button is clicked.
     * This method invokes `handleRating` with a star rating of 2 to update the
     * UI components and display the selected rating.
     */
    @FXML
    private void on2starButtonClicked() {
        handleRating(2);
    }

    /**
     * Handles the action performed when the 3-star rating button is clicked.
     * This method invokes `handleRating` with a parameter value of 3,
     * updating the UI components to reflect the 3-star rating selection.
     */
    @FXML
    private void on3starButtonClicked() {
        handleRating(3);
    }

    /**
     * Handles the action performed when the 4-star rating button is clicked.
     * This method invokes `handleRating` with a parameter value of 4, updating the UI components
     * to reflect the 4-star rating selection.
     */
    @FXML
    private void on4starButtonClicked() {
        handleRating(4);
    }

    /**
     * Handles the action performed when the 5-star rating button is clicked.
     * This method invokes {@code handleRating} with a parameter value of 5,
     * updating the UI components to reflect the 5-star rating selection.
     */
    @FXML
    private void on5starButtonClicked() {
        handleRating(5);
    }

    /**
     * Handles the action event triggered when the post comment button is clicked.
     * This method invokes the handlePostCommentButton method, which processes
     * the posting of a user's comment along with their star rating.
     */
    @FXML
    private void onPostCommentClicked() {
        handlePostCommentButton(stars);
    }

    /**
     * Handles the action performed when the edit review button is clicked.
     * This method triggers the handleEditReviewButton function,
     * which manages the logic for editing an existing review.
     */
    @FXML
    private void onEditReviewClicked() {
        handleEditReviewButton();
    }

    /**
     * Handles the logic when the borrow button is clicked.
     *
     * This method performs the following actions:
     * 1. Checks if the book has not been borrowed yet by evaluating the isBorrowed flag.
     * 2. Displays a dialog box prompting the user to select a due date for borrowing the book,
     *    with a default due date set to one week from the current date.
     * 3. Validates the selected due date to ensure it is a future date.
     * 4. If the selected due date is invalid (i.e., a past date), it displays an error message
     *    and prompts the user to select a future date.
     * 5. If the selected due date is valid, it creates a new loan record with the current date,
     *    the selected due date, the book's information, and the user's ID, then saves it through
     *    the loan service.
     * 6. Disables the borrow button and enables the return button to reflect the borrowed state.
     *
     * Note: This method assumes that there are utility methods and classes like AlertUtil,
     * LoanService, and SessionManager to assist with displaying dialogs, managing loans,
     * and accessing session information, respectively.
     */
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

    /**
     * Handles the logic when the return button is clicked.
     *
     * This method performs the following actions:
     * 1. Checks if the book is currently borrowed by evaluating the isBorrowed flag.
     * 2. Retrieves the loan record of the borrowed book using the LoanService.
     * 3. Displays a success message informing the user of the successful return of the book.
     * 4. Removes the loan record through the LoanService to mark the book as returned.
     * 5. Enables the borrow button and disables the return button to reflect the returned state.
     * 6. Sets the isBorrowed flag to false.
     */
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

    /**
     * Updates the rating display graphics and text based on the provided star rating.
     *
     * This method performs the following actions:
     * <ol>
     *   <li>Sets the class' star rating to the specified value.</li>
     *   <li>Generates a list of image views corresponding to the star ratings using the LayoutUtils.</li>
     *   <li>Assigns the generated star graphics to the star buttons to visually represent the rating.</li>
     *   <li>Updates the reviewRate text to show the selected rating out of 5.</li>
     * </ol>
     *
     * @param stars The number of stars to set the rating to. This should be a value between 1 and 5 inclusive.
     */
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

    /**
     * Handles the logic when the post comment button is clicked.
     *
     * This method performs the following actions:
     * 1. Retrieves and trims the text from the review area.
     * 2. Checks if the comment is not empty.
     * 3. If the comment is not empty, creates a new DocumentEvaluation instance and adds it via the evaluationService.
     * 4. Displays a success dialog and clears the review area.
     * 5. Refreshes the displayed reviews and updates the review status.
     * 6. Updates the book's average rating and persists the changes.
     * 7. Updates the rating display.
     * 8. If the comment is empty, displays an error alert prompting the user to write a comment before posting.
     *
     * @param stars The star rating provided by the user, typically between 1 and 5.
     */
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

    /**
     * Handles the logic for when the Edit Review button is clicked.
     *
     * This method performs the following actions:
     * 1. Retrieves the user's current review for the book using the evaluation service.
     * 2. Removes the existing review.
     * 3. Calls the checkReviewed method to update the review state.
     * 4. Refreshes the displayed reviews by invoking the displayReviews method.
     */
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

    /**
     * Displays reviews for the book currently loaded in the mediator.
     *
     * This method clears the review display area and populates it with reviews
     * fetched from the evaluation service using the ISBN-13 of the currently
     * detailed book. If no reviews are found, a placeholder label indicating
     * "No reviews yet." is shown.*/
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

    /**
     * Adds a hover effect to a button by scaling it up when the mouse enters
     * and scaling it down when the mouse exits.
     *
     * @param button the Button to which the hover effect will be applied
     */
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

    /**
     * Checks if the current book has been marked (bookmarked) by the user.
     * This method uses the BookmarkService to search for a bookmark based on
     * the userId and the ISBN of the current book. If no bookmark is found,
     * the 'isMarked' state will be set to false. If a bookmark is found,
     * the 'isMarked' state will be set to true.
     */
    private void checkMarked() {
        BookmarkService bookmarkService = new BookmarkService();
        Bookmark bookmark = bookmarkService.findById(SessionManager.user.getUserId(), Mediator.bookDetail.getIsbn13());

        if (bookmark == null) {
            isMarked = false;
        } else {
            isMarked = true;
        }
    }

    /**
     * Checks if the currently logged-in user has borrowed the book specified in the system.
     * The function determines the borrowing state by querying the LoanService.
     * It then updates the status of the borrow and return buttons accordingly.
     */
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

    /**
     * This method checks if the current user has reviewed the book specified by its ISBN13.
     * It updates the UI components accordingly based on whether the user has provided
     * a review or not.
     *
     * The UI is adjusted as follows:
     * - Clears any previous children from the existing review box.
     * - If the book ISBN13 is null, it shows a message indicating there are no reviews yet,
     *   hides the rate and review box, and adjusts the visibility and management status of
     *   related UI components.
     * - If the user has already evaluated the book, it hides the rate and review box,
     *   displays the user's review in the existing review box, and shows the edit review button.
     * - If the user has not evaluated the book, it shows the rate and review box, hides
     *   the existing review box, and hides the edit review button.
     */
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

    /**
     * Updates the ratings display for the book. This method retrieves the average
     * ratings from the evaluation service for the book identified by its ISBN13.
     * If the retrieved rating is not zero, it sets this rating in the display.
     * Otherwise, it sets the display to show the average rating from the book details.
     */
    private void updateRatings() {
        if (evaluationService.getAvgRatings(Mediator.bookDetail.getIsbn13()) != 0.0) {
            rates.setText(String.valueOf(evaluationService.getAvgRatings(Mediator.bookDetail.getIsbn13())));
        } else {
            rates.setText(String.valueOf(Mediator.bookDetail.getAverageRating()));
        }
    }
}
