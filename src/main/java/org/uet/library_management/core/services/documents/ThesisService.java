package org.uet.library_management.core.services.documents;

import org.uet.library_management.core.entities.documents.Thesis;
import org.uet.library_management.core.repositories.documents.ThesisRepository;

/**
 * ThesisService is a specialized service class that handles operations
 * related to Thesis documents. It extends from DocumentService and
 * is responsible for interfacing with the ThesisRepository to manage
 * Thesis-specific data.
 */
public class ThesisService extends DocumentService<Thesis> {
    /**
     * Constructs a new instance of the ThesisService class.
     * This class extends the DocumentService class specifically
     * for Thesis documents and interacts with the ThesisRepository
     * for data management operations.
     */
    public ThesisService() {
        super();
    }

    /**
     * Initializes the repository instance to a new ThesisRepository.
     * This method is invoked by the constructor to ensure that the
     * ThesisService operates with the correct repository class for
     * managing thesis-specific data.
     */
    @Override
    protected void loadRepository() {
        repository = new ThesisRepository();
    }
}
