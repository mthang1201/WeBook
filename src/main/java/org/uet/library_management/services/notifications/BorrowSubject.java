package org.uet.library_management.services.notifications;

import java.util.List;

public class BorrowSubject {
    protected List<BorrowObserver> observers;

    public void addObserver(BorrowObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(BorrowObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (BorrowObserver observer : observers) {
            observer.update();
        }
    }
}
