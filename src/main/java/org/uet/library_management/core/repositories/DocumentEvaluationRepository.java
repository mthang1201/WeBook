package org.uet.library_management.core.repositories;

import org.uet.library_management.ConnectJDBC;
import org.uet.library_management.core.entities.DocumentEvaluation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DocumentEvaluationRepository implements MySQLRepository<DocumentEvaluation> {

    private final String db_table;
    private final ConnectJDBC connectJDBC;

    public DocumentEvaluationRepository() {
        connectJDBC = new ConnectJDBC();
        db_table = "documentEvaluations";
    }

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

    @Override
    public int countAll() {
        String query = "SELECT COUNT(*) AS total FROM " + db_table;
        try (ResultSet rs = connectJDBC.executeQuery(query)) {
            return rs.next() ? rs.getInt("total") : 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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

    @Override
    public void add(DocumentEvaluation evaluation) {
        String query = "INSERT INTO " + db_table + " (isbn13, userId, rating, comment, createdAt) VALUES (?, ?, ?, ?, ?)";
        connectJDBC.executeUpdate(query, evaluation.getIsbn13(), evaluation.getUserId(), evaluation.getRating(),
                evaluation.getComment(), evaluation.getCreatedAt());
    }

    @Override
    public void update(DocumentEvaluation evaluation) {
        String query = "UPDATE " + db_table + " SET rating = ?, comment = ?, createdAt = ? WHERE evaluationId = ?";
        connectJDBC.executeUpdate(query, evaluation.getRating(), evaluation.getComment(), evaluation.getCreatedAt(),
                evaluation.getEvaluationId());
    }

    @Override
    public void remove(DocumentEvaluation evaluation) {
        String query = "DELETE FROM " + db_table + " WHERE evaluationId = ?";
        connectJDBC.executeUpdate(query, evaluation.getEvaluationId());
    }

    @Override
    public void removeAll() {
        String query = "DELETE FROM " + db_table;
        connectJDBC.executeUpdate(query);
    }
}
