module org.uet.library_management {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.sql;


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
}