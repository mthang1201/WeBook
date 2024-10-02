package org.uet.library_management.repositories.documents;

public class ThesisRepository extends DocumentRepository {
    public ThesisRepository() {
        super();
    }

    @Override
    protected void loadDatabase() {
        db_table = "thesis";
    }
}
