package org.uet.library_management.services.documents;

import org.uet.library_management.repositories.documents.ThesisRepository;

public class ThesisService extends DocumentService {
    public ThesisService() {
        super();
    }

    @Override
    protected void loadRepository() {
        repository = new ThesisRepository();
    }
}
