package org.uet.library_management.notifications;

public interface LoanSubject {
    void addObserver();
    void removeObserver();
    void notifyObserver();
}
