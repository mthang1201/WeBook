package org.uet.library_management.core.entities;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Represents a loan record in a library management system.
 * A Loan object holds information about a book loaned by a user, including loan date,
 * due date, return date, status, ISBN, title, and user ID of the borrower.
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

    /**
     * Constructs a new Loan object with the specified details.
     *
     * @param loanDate the date the loan was made
     * @param dueDate the date the loan is due
     * @param returnDate the date the loan was returned (can be null if not yet returned)
     * @param status the current status of the loan (e.g., "borrowed", "returned")
     * @param isbn13 the ISBN-13 of the book being loaned
     * @param title the title of the book being loaned
     * @param userId the ID of the user who borrowed the book
     */
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