module com.example.kampregprogram {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.kampregprogram to javafx.fxml;
    exports com.example.kampregprogram;
}