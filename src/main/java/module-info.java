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
    requires jbcrypt;

    opens org.uet.library_management to javafx.fxml;
    opens org.uet.library_management.ui to javafx.fxml;
//    opens org.uet.library_management.ui.auth to javafx.fxml;
//    opens org.uet.library_management.core.entities to javafx.fxml;
//    opens org.uet.library_management.core.entities.documents to javafx.fxml;
//    opens org.uet.library_management.core.repositories to javafx.fxml;
//    opens org.uet.library_management.core.repositories.documents to javafx.fxml;
//    opens org.uet.library_management.core.services to javafx.fxml;
//    opens org.uet.library_management.core.services.documents to javafx.fxml;

    exports org.uet.library_management;
    exports org.uet.library_management.ui;
    exports org.uet.library_management.ui.auth;
    exports org.uet.library_management.core.entities;
    exports org.uet.library_management.core.entities.documents;
    exports org.uet.library_management.core.repositories;
    exports org.uet.library_management.core.repositories.documents;
    exports org.uet.library_management.core.services;
    exports org.uet.library_management.core.services.documents;


}