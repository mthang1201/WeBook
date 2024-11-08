package org.uet.library_management.notifications;

import org.uet.library_management.core.entities.Loan;
import org.uet.library_management.core.services.LoanService;
import org.uet.library_management.tools.AlertUtil;
import org.uet.library_management.tools.SessionManager;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Notification {
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
