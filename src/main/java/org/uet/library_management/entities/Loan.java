package org.uet.library_management.entities;

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

    private String comments;

    private String userId;
}