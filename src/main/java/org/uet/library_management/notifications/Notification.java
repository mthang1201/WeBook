package org.uet.library_management.notifications;

import org.uet.library_management.core.entities.Loan;
import org.uet.library_management.core.services.LoanService;
import org.uet.library_management.tools.AlertUtil;
import org.uet.library_management.tools.SessionManager;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * The Notification class provides functionality to check due dates of loans
 * and alert the user when a loan is due soon or overdue.
 */
public class Notification {
    /**
     * Checks the due dates of all loans for the currently logged-in user and triggers warnings
     * if any loan is due in 2 days or is overdue.
     *
     * This method retrieves all loans for the user from the LoanService, compares the due dates
     * with today's date, and uses the AlertUtil to show warning alerts accordingly.
     */
    public static void checkDueDate() {
        LoanService loanService = new LoanService();
        List<Loan> loans = loanService.findByUserId(SessionManager.user.getUserId());
        LocalDate today = LocalDate.now();
        for (Loan loan : loans) {
            String status = loan.getStatus();
            LocalDate dueDate = LocalDate.parse(loan.getDueDate());

            if (ChronoUnit.DAYS.between(today, dueDate) == 2) {
                AlertUtil.showWarningAlert(
                        "Reminder",
                        "Your loan for \"" + loan.getTitle() + "\" is due soon! (2 days left)",
                        null,
                        null
                );
            } else if (today.isAfter(dueDate)) {
                AlertUtil.showWarningAlert(
                        "Overdue",
                        "Your loan for " + loan.getTitle() + " is overdue!",
                        "Please return " + loan.getTitle() + " as soon as possible or you will be fined!",
                        null
                );
            }
        }
    }
}
