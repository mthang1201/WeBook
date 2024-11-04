package org.uet.library_management.core.entities;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Loan.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Loan {
    private int loanId;

    private String loanDate;

    private String dueDate;

    private String returnDate;

    private String status;

    private String isbn13;

    private String title;

    private int userId;

    public Loan(String loanDate, String dueDate, String returnDate, String status, String isbn13, String title, int userId) {
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
        this.isbn13 = isbn13;
        this.title = title;
        this.userId = userId;
    }
}