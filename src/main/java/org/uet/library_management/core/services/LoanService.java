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

}
