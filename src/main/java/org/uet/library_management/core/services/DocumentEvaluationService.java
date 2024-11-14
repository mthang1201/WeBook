package org.uet.library_management.core.services;

import org.uet.library_management.core.entities.DocumentEvaluation;
import org.uet.library_management.core.repositories.DocumentEvaluationRepository;

import java.util.List;

/**
 * Service class for handling operations related to DocumentEvaluation.
 * This service class provides methods to interact with the
 * DocumentEvaluationRepository to fetch, add, update, and remove
 * document evaluations.
 */
public class DocumentEvaluationService {
    private final DocumentEvaluationRepository repository;

    /**
     * Constructor for DocumentEvaluationService.
     * Initializes the service with a new instance of DocumentEvaluationRepository.
     */
    public DocumentEvaluationService() {
        this.repository = new DocumentEvaluationRepository();
    }

    /**
     * Retrieves a review for a specific document created by a particular user.
     *
     * @param isbn13 the ISBN-13 code of the document
     * @param userId the ID of the user who created the review
     * @return a DocumentEvaluation object representing the user review if found, otherwise null
     */
    public DocumentEvaluation getUserReview(String isbn13, int userId) {
        return repository.getUserReview(isbn13, userId);
    }

    /**
     * Checks if a given user has evaluated a document identified by its ISBN-13 code.
     *
     * @param isbn13 the ISBN-13 code of the document
     * @param userId the ID of the user who might have evaluated the document
     * @return true if the user has evaluated the document, false otherwise
     */
    public boolean hasEvaluated(String isbn13, int userId) {
        return repository.hasEvaluated(isbn13, userId);
    }

    /**
     * Retrieves the average rating for a document identified by its ISBN-13 code.
     *
     * @param isbn13 the ISBN-13 code of the document.
     * @return the average rating of the document as a double. If no ratings are found, it returns 0.0.
     */
    public double getAvgRatings(String isbn13) {
        return repository.getAvgRatings(isbn13);
    }


    /**
     * Retrieves all document evaluations from the repository.
     *
     * @return a list of DocumentEvaluation objects representing all
     *         document evaluations stored in the repository.
     */
    public List<DocumentEvaluation> findAll() {
        return repository.findAll();
    }

    /**
     * Retrieves a paginated list of DocumentEvaluation objects.
     *
     * @param page the number of the page to retrieve
     * @param pageSize the number of items per page
     * @return a list of DocumentEvaluation objects for the specified page
     */
    public List<DocumentEvaluation> findAllByPage(int page, int pageSize) {
        return repository.findAllByPage(page, pageSize);
    }

    public int countAll() {
        return repository.countAll();
    }

    /**
     * Finds and retrieves a list of DocumentEvaluation objects based on the specified ISBN-13 code.
     *
     * @param isbn13 the ISBN-13 code of the document for which evaluations are to be retrieved
     * @return a list of DocumentEvaluation objects associated with the specified ISBN-13 code
     */
    public List<DocumentEvaluation> findByIsbn13(String isbn13) {
        return repository.findByIsbn13(isbn13);
    }

    /**
     * Retrieves a list of DocumentEvaluation objects for a specific user.
     *
     * @param userId the ID of the user whose document evaluations are to be retrieved
     * @return a list of DocumentEvaluation objects for the specified user
     */
    public List<DocumentEvaluation> findByUserId(int userId) {
        return repository.findByUserId(userId);
    }

    /**
     * Adds a new DocumentEvaluation to the repository.
     *
     * @param evaluation the DocumentEvaluation object containing the details
     *                   such as ISBN-13, user ID, rating, comment, and
     *                   creation timestamp.
     */
    public void add(DocumentEvaluation evaluation) {
        repository.add(evaluation);
    }

    /**
     * Updates an existing DocumentEvaluation in the repository.
     *
     * @param evaluation the DocumentEvaluation object containing the updated details
     *                   such as rating, comment, and creation timestamp.
     */
    public void update(DocumentEvaluation evaluation) {
        repository.update(evaluation);
    }

    /**
     * Removes the specified DocumentEvaluation from the repository.
     *
     * @param evaluation the DocumentEvaluation object to be removed
     */
    public void remove(DocumentEvaluation evaluation) {
        repository.remove(evaluation);
    }

    /**
     * Removes all DocumentEvaluation records from the repository.
     *
     * This method interfaces with the DocumentEvaluationRepository to delete
     * all entries, effectively clearing the repository. It uses the repository's
     * removeAll method, which executes a SQL DELETE statement to remove all
     * document evaluations from the associated database table.
     *
     * @throws RuntimeException if a database access error occurs while attempting
     * to remove the records.
     */
    public void removeAll() {
        repository.removeAll();
    }
}
