package org.uet.library_management.core.services;

import org.uet.library_management.core.entities.Loan;
import org.uet.library_management.core.repositories.LoanRepository;

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

    public Loan findById(int userId, String isbn13) {
        return repository.findById(userId, isbn13);
    }



    public void add(Loan loan) {
        repository.add(loan);
    }

    public void update(Loan loan) {
        repository.update(loan);
    }

    public void remove(Loan loan) {
        repository.remove(loan);
    }

    public void removeAll() {
        repository.removeAll();
    }

}
