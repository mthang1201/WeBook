package org.uet.library_management.core.repositories;

import org.uet.library_management.ConnectJDBC;
import org.uet.library_management.core.entities.DocumentEvaluation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DocumentEvaluationRepository is a repository class that manages database operations
 * for DocumentEvaluation entities in a MySQL database. It provides methods for
 * retrieving, adding, updating, and deleting document evaluations in the database.
 */
public class DocumentEvaluationRepository implements MySQLRepository<DocumentEvaluation> {

    private final String db_table;
    private final ConnectJDBC connectJDBC;

    /**
     * Constructor for the DocumentEvaluationRepository class.
     * Initializes the connection to the database using the ConnectJDBC class
     * and sets the database table name to "documentEvaluations".
     */
    public DocumentEvaluationRepository() {
        connectJDBC = new ConnectJDBC();
        db_table = "documentEvaluations";
    }

    /**
     * Helper method to map the ResultSet to a DocumentEvaluation object.
     *
     * @param rs*/
    // Helper method to map the ResultSet to a DocumentEvaluation object
    private DocumentEvaluation populateDocumentEvaluation(ResultSet rs) throws SQLException {
        return new DocumentEvaluation(
                rs.getInt("evaluationId"),
                rs.getString("isbn13"),
                rs.getInt("userId"),
                rs.getInt("rating"),
                rs.getString("comment"),
                rs.getTimestamp("createdAt")
        );
    }

    /**
     * Retrieves a user review for a document identified by ISBN-13 and user ID.
     *
     * @param isbn13 the ISBN-13 code of the document.
     * @param userId the ID of the user who created the review.
     * @return a DocumentEvaluation object if found, otherwise null.
     */
    public DocumentEvaluation getUserReview(String isbn13, int userId) {
        String query = "SELECT * FROM " + db_table + " WHERE isbn13 = '" + isbn13 + "' AND userId = '" + userId + "'";
        try (ResultSet rs = connectJDBC.executeQuery(query)) {
            if (rs.next()) {
                return populateDocumentEvaluation(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Checks if a given user has evaluated a document identified by its ISBN-13 code.
     *
     * @param isbn13 the ISBN-13 code of the document
     * @param userId the ID of the user who might have evaluated the document
     * @return true if the user has evaluated the document, false otherwise
     */
    public boolean hasEvaluated(String isbn13, int userId) {
        String query = "SELECT COUNT(*) FROM " + db_table + "  WHERE isbn13 = '" + isbn13 + "' AND userId = '" + userId + "'";
        try (ResultSet rs = connectJDBC.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * Retrieves the average rating for a document identified by its ISBN-13 code.
     *
     * @param isbn13 the ISBN-13 code of the document.
     * @return the average rating of the document as a double.
     *         If no ratings are found, it returns 0.0.
     * @throws RuntimeException if a database access error occurs.
     */
    public double getAvgRatings(String isbn13) {
        String query = "SELECT AVG(rating) AS avgRating FROM " + db_table + " WHERE isbn13 = ?";
        try (ResultSet rs = connectJDBC.executeQueryWithParams(query, isbn13)) {
            if (rs.next()) {
                return rs.getDouble("avgRating");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0.0;
    }


    /**
     * Retrieves all document evaluations from the database.
     *
     * @return a list of DocumentEvaluation objects representing all
     *         document evaluations stored in the database.
     * @throws RuntimeException if a database access error occurs.
     */
    @Override
    public List<DocumentEvaluation> findAll() {
        String query = "SELECT * FROM " + db_table;
        try (ResultSet rs = connectJDBC.executeQuery(query)) {
            List<DocumentEvaluation> evaluations = new ArrayList<>();
            while (rs.next()) {
                evaluations.add(populateDocumentEvaluation(rs));
            }
            return evaluations;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves a paginated list of DocumentEvaluation objects from the database.
     *
     * @param page the number of the page to retrieve.
     * @param pageSize the number of items per page.
     * @return a list of DocumentEvaluation objects for the specified page.
     */
    @Override
    public List<DocumentEvaluation> findAllByPage(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        String query = "SELECT * FROM " + db_table + " LIMIT " + pageSize + " OFFSET " + offset;

        try (ResultSet rs = connectJDBC.executeQuery(query)) {
            List<DocumentEvaluation> evaluations = new ArrayList<>();
            while (rs.next()) {
                evaluations.add(populateDocumentEvaluation(rs));
            }
            return evaluations;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Counts the total number of records in the database table associated with the DocumentEvaluationRepository.
     *
     * @return the total count of records in the associated database table.
     * @throws RuntimeException if a database access error occurs.
     */
    @Override
    public int countAll() {
        String query = "SELECT COUNT(*) AS total FROM " + db_table;
        try (ResultSet rs = connectJDBC.executeQuery(query)) {
            return rs.next() ? rs.getInt("total") : 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Finds and retrieves a list of DocumentEvaluation objects from the database based on the specified ISBN-13 code.
     *
     * @param isbn13 the ISBN-13 code of the document for which evaluations are to be retrieved.
     * @return a list of DocumentEvaluation objects associated with the specified ISBN-13 code.
     */
    public List<DocumentEvaluation> findByIsbn13(String isbn13) {
        String query = "SELECT * FROM " + db_table + " WHERE isbn13 = ?";
        try (ResultSet rs = connectJDBC.executeQueryWithParams(query, isbn13)) {
            List<DocumentEvaluation> evaluations = new ArrayList<>();
            while (rs.next()) {
                evaluations.add(populateDocumentEvaluation(rs));
            }
            return evaluations;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves a list of DocumentEvaluation objects for a specific user from the database.
     *
     * @param userId the ID of the user whose document evaluations are to be retrieved
     * @return a list of DocumentEvaluation objects for the specified user
     */
    public List<DocumentEvaluation> findByUserId(int userId) {
        String query = "SELECT * FROM " + db_table + " WHERE userId = ?";
        try (ResultSet rs = connectJDBC.executeQueryWithParams(query, userId)) {
            List<DocumentEvaluation> evaluations = new ArrayList<>();
            while (rs.next()) {
                evaluations.add(populateDocumentEvaluation(rs));
            }
            return evaluations;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a new document evaluation to the database.
     *
     * @param evaluation the DocumentEvaluation object containing details such as ISBN-13,
     *                   user ID, rating, comment, and creation timestamp.
     */
    @Override
    public void add(DocumentEvaluation evaluation) {
        String query = "INSERT INTO " + db_table + " (isbn13, userId, rating, comment, createdAt) VALUES (?, ?, ?, ?, ?)";
        connectJDBC.executeUpdate(query, evaluation.getIsbn13(), evaluation.getUserId(), evaluation.getRating(),
                evaluation.getComment(), evaluation.getCreatedAt());
    }

    /**
     * Updates an existing document evaluation in the database.
     *
     * @param evaluation the DocumentEvaluation object containing the updated details
     *                   such as rating, comment, and creation timestamp.
     */
    @Override
    public void update(DocumentEvaluation evaluation) {
        String query = "UPDATE " + db_table + " SET rating = ?, comment = ?, createdAt = ? WHERE evaluationId = ?";
        connectJDBC.executeUpdate(query, evaluation.getRating(), evaluation.getComment(), evaluation.getCreatedAt(),
                evaluation.getEvaluationId());
    }

    /**
     * Removes a document evaluation record from the database.
     *
     * @param evaluation the DocumentEvaluation object containing the details of the evaluation
     *                   to be removed, including its evaluation ID.
     */
    @Override
    public void remove(DocumentEvaluation evaluation) {
        String query = "DELETE FROM " + db_table + " WHERE evaluationId = ?";
        connectJDBC.executeUpdate(query, evaluation.getEvaluationId());
    }

    /**
     * Removes all records from the database table associated with the
     * DocumentEvaluationRepository.
     *
     * This method constructs and executes a SQL DELETE statement to remove
     * all entries in the specified database table.
     *
     * @throws RuntimeException if a database access error occurs.
     */
    @Override
    public void removeAll() {
        String query = "DELETE FROM " + db_table;
        connectJDBC.executeUpdate(query);
    }
}
