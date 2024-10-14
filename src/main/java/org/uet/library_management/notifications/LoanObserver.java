package org.uet.library_management.notifications;

public interface LoanObserver {
    void update(String documentID, String loanDate, String dueDate);
}
