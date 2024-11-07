package org.uet.library_management.core.services;

import org.uet.library_management.core.entities.Loan;
import org.uet.library_management.core.repositories.LoanRepository;
import org.uet.library_management.tools.AlertUtil;
import org.uet.library_management.tools.SessionManager;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class LoanService {
    private final LoanRepository repository;

    public LoanService() {
        this.repository = new LoanRepository();
    }

    public List<Loan> findAll() {
        return repository.findAll();
    }

    public List<Loan> findAllByPage(int page, int pageSize) {
        return repository.findAllByPage(page, pageSize);
    }

    public int countAll() {
        return repository.countAll();
    }

    public List<Loan> findByUserId(int userId) {
        return repository.findByUserId(userId);
    }

    public void add(Loan loan) {
        repository.add(loan);
    }

    public void update(Loan user) {
        repository.update(user);
    }

    public void remove(Loan user) {
        repository.remove(user);
    }

    public void removeAll() {
        repository.removeAll();
    }

    public static void checkDueDate() {
        LoanService loanService = new LoanService();
        List<Loan> loans = loanService.findByUserId(SessionManager.user.getUserId());
        LocalDate today = LocalDate.now();
        for (Loan loan : loans) {
            String status = loan.getStatus();
            if (status.equals("returned")) {
                continue;
            } else {
                LocalDate dueDate = LocalDate.parse(loan.getDueDate());

                if (status.equals("borrowed") && ChronoUnit.DAYS.between(today, dueDate) == 2) {
                    AlertUtil.showWarningAlert(
                            "Reminder",
                            "Your loan for " + loan.getTitle() + " is due soon! (2 days left)",
                            null,
                            null
                    );
                } else if (status.equals("borrowed") && today.isAfter(dueDate)) {
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
}
