module org.example.project_3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.project_3 to javafx.fxml;
    exports org.example.project_3;
}