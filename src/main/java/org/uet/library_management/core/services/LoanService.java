package org.uet.library_management.core.services;

import org.uet.library_management.core.entities.Loan;
import org.uet.library_management.core.repositories.LoanRepository;

import java.util.List;

/**
 * Service class to handle the business logic for loans.
 * This class interacts with the LoanRepository to perform various loan-related operations.
 */
public class LoanService {
    private final LoanRepository repository;

    /**
     * Constructs a new LoanService.
     *
     * This constructor initializes the LoanService by setting up a connection
     * to the LoanRepository which manages the database operations for loans.
     */
    public LoanService() {
        this.repository = new LoanRepository();
    }

    /**
     * Has overdue loan boolean.
     *
     * @param userId the user id
     * @return the boolean
     */
    public boolean hasOverdueLoan(int userId) {
        return repository.hasOverdueLoan(userId);
    }

    /**
     * Retrieves all loan records.
     *
     * @return a list of Loan objects representing all loans
     */
    public List<Loan> findAll() {
        return repository.findAll();
    }

    /**
     * Retrieves a paginated list of loan records.
     *
     * @param page the page number to retrieve, starting from 1
     * @param pageSize the number of records per page
     * @return a list of Loan objects for the specified page
     */
    public List<Loan> findAllByPage(int page, int pageSize) {
        return repository.findAllByPage(page, pageSize);
    }

    /**
     * Counts the total number of loan records.
     *
     * @return the total number of loans
     */
    public int countAll() {
        return repository.countAll();
    }

    /**
     * Finds and retrieves all loan records associated with a specific user ID.
     *
     * @param userId the ID of the user whose loan records are to be retrieved
     * @return a list of Loan objects associated with the specified user ID
     */
    public List<Loan> findByUserId(int userId) {
        return repository.findByUserId(userId);
    }

    /**
     * Finds a loan record by user ID and ISBN-13.
     *
     * @param userId the ID of the user who borrowed the book
     * @param isbn13 the ISBN-13 of the book being loaned
     * @return the Loan object if found, or null if no matching record is found
     */
    public Loan findById(int userId, String isbn13) {
        return repository.findById(userId, isbn13);
    }



    /**
     * Adds a new loan record.
     *
     * @param loan the Loan object representing the loan to be added
     */
    public void add(Loan loan) {
        repository.add(loan);
    }

    /**
     * Updates the details of an existing loan record.
     *
     * @param loan the Loan object containing updated details that need to be saved
     */
    public void update(Loan loan) {
        repository.update(loan);
    }

    /**
     * Removes a specified loan record.
     *
     * @param loan the Loan object representing the loan to be removed
     */
    public void remove(Loan loan) {
        repository.remove(loan);
    }

    /**
     * Removes all loan records from the repository.
     *
     * This method interacts with the LoanRepository to delete all existing loan records
     * from the underlying database or data storage.
     */
    public void removeAll() {
        repository.removeAll();
    }

}
