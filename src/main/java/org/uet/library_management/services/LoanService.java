package org.uet.library_management.services;

import org.uet.library_management.entities.Loan;
import org.uet.library_management.repositories.LoanRepository;

import java.util.List;

public class LoanService {
    private LoanRepository repository;

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

    public List<Loan> findById(int id) {
        return repository.findById(id);
    }

    public void add(Loan user) {
        repository.add(user);
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
