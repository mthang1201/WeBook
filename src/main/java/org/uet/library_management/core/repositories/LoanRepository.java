package org.uet.library_management.core.repositories;

import org.uet.library_management.ConnectJDBC;
import org.uet.library_management.core.entities.Loan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoanRepository implements MySQLRepository<Loan> {
    private final String db_table;

    private final ConnectJDBC connectJDBC;

    public LoanRepository() {
        connectJDBC = new ConnectJDBC();
        db_table = "loans";
    }

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

    public List<Loan> findByUserId(int userId) {
        List<Loan> loans = new ArrayList<>();
        String query = "SELECT * FROM " + db_table + " WHERE userId LIKE ?";

        try (ResultSet rs = connectJDBC.executeQueryWithParams(query, userId)) {
            while (rs.next()) {
                loans.add(populateLoan(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return loans;
    }

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
    @Override
    public void add(Loan loan) {
        String query = "INSERT INTO " + db_table + " (loanDate, dueDate, returnDate, status, " +
                "isbn13, title, userId) VALUES (?, ?, ?, ?, ?, ?, ?)";
        connectJDBC.executeUpdate(query, loan.getLoanDate(), loan.getDueDate(),
                loan.getReturnDate(), loan.getStatus(), loan.getIsbn13(), loan.getTitle(), loan.getUserId());
    }

    @Override
    public void update(Loan loan) {
        String query = "UPDATE " + db_table + " SET loanDate = ?, dueDate = ?, returnDate = ?, " +
                "status = ?, userId = ?, title = ?, WHERE loanId = ?";
//        connectJDBC.executeUpdate(query, loan.getLoanDate(), loan.getDueDate(),
//                loan.getReturnDate(), loan.getStatus(), loan.getComments(), loan.getLoanId(),
//                loan.getLoanId());
    }

    @Override
    public void remove(Loan loan) {
        String query = "DELETE FROM " + db_table + " WHERE loanId = ?";
        connectJDBC.executeUpdate(query, loan.getLoanId());
    }

    @Override
    public void removeAll() {
        String query = "DELETE FROM " + db_table;
        connectJDBC.executeUpdate(query);
    }

}
