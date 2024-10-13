package org.uet.library_management.services.notifications;

import org.uet.library_management.entities.Loan;

import java.util.List;

public class BeforeDueDateSubject {
    private List<LoanObserver> observers;
    private Loan loan;
    public void addObserver(LoanObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(LoanObserver observer) {
        observers.remove(observer);
    }
    //ban da muon cuon sach "bookName" tu ngay loanDate va se phai tra truoc ngay DueDate
    //ban da qua han tra cuon sach "bookName", se bi phat.
    public void notifyObservers() {
        for (LoanObserver observer : observers) {
            observer.update(loan.getDocumentId(), loan.getLoanDate(), loan.getDueDate());
        }
    }
}
