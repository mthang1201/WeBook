package org.uet.library_management.entities;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * LoanDetails.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDetails {
    private int loanId;

    private String documentId;

    private int quantityLoaned;
}
