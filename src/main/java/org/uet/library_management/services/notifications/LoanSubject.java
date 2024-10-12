package org.uet.library_management.services.notifications;

public interface LoanSubject {
    void addObserver();
    void removeObserver();
    void notifyObserver();
}
