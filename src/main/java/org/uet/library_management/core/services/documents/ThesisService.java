package org.uet.library_management.core.services.documents;

import org.uet.library_management.core.entities.documents.Thesis;
import org.uet.library_management.core.repositories.documents.ThesisRepository;

public class ThesisService extends DocumentService<Thesis> {
    public ThesisService() {
        super();
    }

    @Override
    protected void loadRepository() {
        repository = new ThesisRepository();
    }
}
