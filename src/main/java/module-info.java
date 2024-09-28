module org.uet.library_management {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.uet.library_management to javafx.fxml;
    exports org.uet.library_management;
}