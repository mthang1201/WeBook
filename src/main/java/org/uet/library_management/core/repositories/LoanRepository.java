package org.uet.library_management.core.repositories;

import org.uet.library_management.ConnectJDBC;
import org.uet.library_management.core.entities.Loan;
import org.uet.library_management.core.entities.documents.Book;
import org.uet.library_management.tools.SessionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * LoanRepository provides CRUD operations for Loan entities in a MySQL database.
 * Implements MySQLRepository interface for managing loan records.
 */
public class LoanRepository implements MySQLRepository<Loan> {
    private final String db_table;

    private final ConnectJDBC connectJDBC;

    /**
     * Constructs a new LoanRepository.
     *
     * This constructor initializes the LoanRepository by setting up a connection to
     * the JDBC (Java Database Connectivity) and defining the database table for loans.
     */
    public LoanRepository() {
        connectJDBC = new ConnectJDBC();
        db_table = "loans";
    }

    /**
     * Populates a Loan object with data from a given ResultSet.
     *
     * @param rs the ResultSet from which the Loan data is retrieved
     * @return a Loan object containing data from the ResultSet
     * @throws SQLException if there is a database access error or other errors
     */
    private Loan populateLoan(ResultSet rs) throws SQLException {
        Loan loan = new Loan();

        loan.setLoanId(rs.getInt("loanId"));
        loan.setLoanDate(rs.getString("loanDate"));
        loan.setDueDate(rs.getString("dueDate"));
        loan.setReturnDate(rs.getString("returnDate"));
        loan.setStatus(rs.getString("status"));
        loan.setIsbn13(rs.getString("isbn13"));
        loan.setTitle(rs.getString("title"));
        loan.setUserId(rs.getInt("userId"));

        return loan;
    }

    /**
     * Retrieves all loan records from the database.
     *
     * @return a list of Loan objects representing all loans in the database
     */
    @Override
    public List<Loan> findAll() {
        List<Loan> loans = new ArrayList<>();
        String query = "SELECT * FROM " + db_table;

        try (ResultSet rs = connectJDBC.executeQuery(query)) {
            while (rs.next()) {
                loans.add(populateLoan(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return loans;
    }

    /**
     * Retrieves a paginated list of loan records from the database.
     *
     * @param page the page number to retrieve, starting from 1
     * @param pageSize the number of records per page
     * @return a list of Loan objects for the specified page
     */
    @Override
    public List<Loan> findAllByPage(int page, int pageSize) {
        List<Loan> loans = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        String query = "SELECT * FROM " + db_table + " LIMIT " + pageSize + " OFFSET " + offset;

        try (ResultSet rs = connectJDBC.executeQuery(query)) {
            while (rs.next()) {
                loans.add(populateLoan(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return loans;
    }

    /**
     * Has overdue loan boolean.
     *
     * @param userId the user id
     * @return the boolean
     */
    public boolean hasOverdueLoan(int userId) {
        String query = "SELECT COUNT(loanId) from " + db_table + " WHERE userId = ? AND dueDate < CURRENT_DATE()";

        try (ResultSet rs = connectJDBC.executeQueryWithParams(query, userId)) {
            while (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * Counts the total number of records in the loan database table.
     *
     * @return the total count of loan records in the database
     */
    @Override
    public int countAll() {
        String query = "SELECT COUNT(*) AS total FROM " + db_table;
        int count = 0;

        try (ResultSet rs = connectJDBC.executeQuery(query)) {
            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    /**
     * Finds and retrieves all loan records associated with a specific user ID.
     *
     * @param userId the ID of the user whose loan records are to be retrieved.
     * @return a list of Loan objects associated with the specified user ID.
     */
    public List<Loan> findByUserId(int userId) {
        List<Loan> loans = new ArrayList<>();
        String query = "SELECT * FROM " + db_table + " WHERE userId = ?";

        try (ResultSet rs = connectJDBC.executeQueryWithParams(query, userId)) {
            while (rs.next()) {
                loans.add(populateLoan(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return loans;
    }

    /**
     * Finds a loan record by user ID and ISBN-13.
     *
     * @param userId the ID of the user who borrowed the book
     * @param isbn13 the ISBN-13 of the book being loaned
     * @return the Loan object if found, or null if no matching record is found
     */
    public Loan findById(int userId, String isbn13) {
        Loan loan = null;
        String query = "SELECT * FROM " + db_table + " WHERE userId = ? AND isbn13 = ?";

        try (ResultSet rs = connectJDBC.executeQueryWithParams(query, userId, isbn13)) {
            if (rs.next()) {
                loan = populateLoan(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return loan;
    }

    /**
     * Adds a new loan record to the database.
     *
     * @param loan the Loan object containing the details of the loan to be added to the database
     */
    @Override
    public void add(Loan loan) {
        String query = "INSERT INTO " + db_table + " (loanDate, dueDate, returnDate, status, " +
                "isbn13, title, userId) VALUES (?, ?, ?, ?, ?, ?, ?)";

        connectJDBC.executeUpdate(query, loan.getLoanDate(), loan.getDueDate(),
                loan.getReturnDate(), loan.getStatus(), loan.getIsbn13(), loan.getTitle(), loan.getUserId());
    }

    /**
     * Updates an existing loan record in the database.
     *
     * @param loan the Loan object containing updated details of the loan to be saved in the database
     */
    @Override
    public void update(Loan loan) {
        String query = "UPDATE " + db_table + " SET loanDate = ?, dueDate = ?, returnDate = ?, " +
                "status = ?, userId = ?, title = ?, WHERE loanId = ?";
//        connectJDBC.executeUpdate(query, loan.getLoanDate(), loan.getDueDate(),
//                loan.getReturnDate(), loan.getStatus(), loan.getComments(), loan.getLoanId(),
//                loan.getLoanId());
    }

    /**
     * Removes a loan record from the database.
     *
     * @param loan the Loan object to be removed
     */
    @Override
    public void remove(Loan loan) {
        String query = "DELETE FROM " + db_table + " WHERE loanId = ?";

        connectJDBC.executeUpdate(query, loan.getLoanId());
    }

    /**
     * Removes all loan records from the database table managed by the LoanRepository.
     * <p>
     * This method executes a DELETE SQL query on the configured database table to remove
     * all records. The table name is specified by the db_table field, and the execution
     * of the query is handled by the connectJDBC component.
     */
    @Override
    public void removeAll() {
        String query = "DELETE FROM " + db_table;

        connectJDBC.executeUpdate(query);
    }
}
