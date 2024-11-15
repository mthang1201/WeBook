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
            LocalDate dueDate = LocalDate.parse(loan.getDueDate());

            if (ChronoUnit.DAYS.between(today, dueDate) == 2) {
                AlertUtil.showWarningAlert(
                        "Nhắc nhở",
                        "Cuốn \"" + loan.getTitle() + "\" sắp hết hạn! (còn 2 ngày)",
                        null,
                        null
                );
            } else if (today.isAfter(dueDate)) {
                AlertUtil.showWarningAlert(
                        "Quá hạn",
                        "Cuốn " + loan.getTitle() + " đã hết hạn!",
                        "Hãy trả cuốn " + loan.getTitle() + " trong thời gian sớm nhất nếu không bạn sẽ bị phạt!",
                        null
                );
            }
        }
    }
}
