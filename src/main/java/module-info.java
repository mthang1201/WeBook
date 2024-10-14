module org.uet.library_management {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.sql;
    requires google.api.client;
    requires com.google.api.client;
    requires com.google.api.services.books;
    requires com.google.api.client.json.gson;
    requires org.apache.commons.text;

    opens org.uet.library_management to javafx.fxml;
    exports org.uet.library_management;
    exports org.uet.library_management.core.repositories;
    opens org.uet.library_management.core.repositories to javafx.fxml;
    exports org.uet.library_management.core.entities;
    opens org.uet.library_management.core.entities to javafx.fxml;
    exports org.uet.library_management.core.entities.documents;
    opens org.uet.library_management.core.entities.documents to javafx.fxml;
    exports org.uet.library_management.ui;
    opens org.uet.library_management.ui to javafx.fxml;
    exports org.uet.library_management.core.repositories.documents;
    opens org.uet.library_management.core.repositories.documents to javafx.fxml;
    exports org.uet.library_management.core.services.documents;
    opens org.uet.library_management.core.services.documents to javafx.fxml;
    exports org.uet.library_management.core.services;
    opens org.uet.library_management.core.services to javafx.fxml;
}