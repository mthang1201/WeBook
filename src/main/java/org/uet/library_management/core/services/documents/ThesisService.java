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
    public ThesisService() {
        super();
    }

    @Override
    protected void loadRepository() {
        repository = new ThesisRepository();
    }
}
