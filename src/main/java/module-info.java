module com.example.kampregprogram {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;


    opens com.example.kampregprogram to javafx.fxml;
    exports com.example.kampregprogram;
    exports com.example.kampregprogram.data;
    opens com.example.kampregprogram.data to javafx.fxml;
}