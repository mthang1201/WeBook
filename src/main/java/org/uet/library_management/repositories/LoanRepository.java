package org.uet.library_management.repositories;

import org.uet.library_management.ConnectJDBC;
import org.uet.library_management.entities.Loan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoanRepository {
    private String db_table;

    private final ConnectJDBC connectJDBC;

    public LoanRepository() {
        connectJDBC = new ConnectJDBC();
        db_table = "loans";
    }
    
    public List<Loan> findAll() {
        List<Loan> loans = new ArrayList<>();

        String query = "SELECT * FROM " + db_table;
        ResultSet rs = connectJDBC.executeQuery(query);
        while (true) {
            try {
                if (!rs.next()) break;
                Loan loan = new Loan();
                loan.setLoanId(rs.getInt("loanId"));
                loan.setLoanDate(rs.getString("loanDate"));
                loan.setDueDate(rs.getString("dueDate"));
                loan.setReturnDate(rs.getString("returnDate"));
                loan.setStatus(rs.getString("status"));
                loan.setComments(rs.getString("comments"));
                loan.setUserId(rs.getString("loanId"));

                loans.add(loan);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return loans;
    }

    public List<Loan> findAllByPage(int page, int pageSize) {
        List<Loan> loans = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        String query = "SELECT * FROM " + db_table + " LIMIT " + pageSize + " OFFSET " + offset;
        ResultSet rs = connectJDBC.executeQuery(query);
        while (true) {
            try {
                if (!rs.next()) break;
                Loan loan = new Loan();
                loan.setLoanId(rs.getInt("loanId"));
                loan.setLoanDate(rs.getString("loanDate"));
                loan.setDueDate(rs.getString("dueDate"));
                loan.setReturnDate(rs.getString("returnDate"));
                loan.setStatus(rs.getString("status"));
                loan.setComments(rs.getString("comments"));
                loan.setUserId(rs.getString("loanId"));

                loans.add(loan);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return loans;
    }

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

    public List<Loan> findById(int id) {
        List<Loan> loans = new ArrayList<>();

        String query = "SELECT * FROM " + db_table + " WHERE id LIKE ?";
        ResultSet rs = connectJDBC.executeQueryWithParams(query, id);
        while (true) {
            try {
                if (!rs.next()) break;
                Loan loan = new Loan();
                loan.setLoanId(rs.getInt("loanId"));
                loan.setLoanDate(rs.getString("loanDate"));
                loan.setDueDate(rs.getString("dueDate"));
                loan.setReturnDate(rs.getString("returnDate"));
                loan.setStatus(rs.getString("status"));
                loan.setComments(rs.getString("comments"));
                loan.setUserId(rs.getString("loanId"));

                loans.add(loan);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return loans;
    }

    public void add(Loan loan) {
        String query = "INSERT INTO " + db_table + " (loanDate, dueDate, returnDate, status, " +
                "comments, userId) VALUES (?, ?, ?, ?, ?, ?)";
        connectJDBC.executeUpdate(query, loan.getLoanDate(), loan.getDueDate(),
                loan.getReturnDate(), loan.getStatus(), loan.getComments(), loan.getUserId());
    }

    public void update(Loan loan) {
        String query = "UPDATE " + db_table + " SET loanDate = ?, dueDate = ?, returnDate = ?, " +
                "status = ?, comments = ?, userId = ? WHERE loanId = ?";
        connectJDBC.executeUpdate(query, loan.getLoanDate(), loan.getDueDate(),
                loan.getReturnDate(), loan.getStatus(), loan.getComments(), loan.getUserId(),
                loan.getLoanId());
    }

    public void remove(Loan loan) {
        String query = "DELETE FROM " + db_table + " WHERE loanId = ?";
        connectJDBC.executeUpdate(query, loan.getLoanId());
    }

    public void removeAll() {
        String query = "DELETE FROM " + db_table;
        connectJDBC.executeUpdate(query);
    }
}
