package com.example.kampregprogram;

import com.example.kampregprogram.data.DataLayer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Timer;
import java.util.TimerTask;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("main-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),800,600);
        stage.setTitle("Kamp Oversigt");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();



    }
}