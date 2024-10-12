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
    exports org.uet.library_management.repositories;
    opens org.uet.library_management.repositories to javafx.fxml;
    exports org.uet.library_management.services;
    opens org.uet.library_management.services to javafx.fxml;
    exports org.uet.library_management.entities;
    opens org.uet.library_management.entities to javafx.fxml;
    exports org.uet.library_management.entities.documents;
    opens org.uet.library_management.entities.documents to javafx.fxml;
    exports org.uet.library_management.ui;
    opens org.uet.library_management.ui to javafx.fxml;
    exports org.uet.library_management.repositories.documents;
    opens org.uet.library_management.repositories.documents to javafx.fxml;
    exports org.uet.library_management.services.documents;
    opens org.uet.library_management.services.documents to javafx.fxml;
}