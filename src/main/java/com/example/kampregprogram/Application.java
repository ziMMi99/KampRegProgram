package com.example.kampregprogram;

import com.example.kampregprogram.data.DataLayer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.sql.Timestamp;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("main-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        DataLayer data = new DataLayer();
        data.insertIntoTest();
        launch();

        MatchEventLog eventLog = new MatchEventLog(24,10,2,EventType.goal,new Timestamp(System.currentTimeMillis()));
        System.out.println(eventLog);


    }
}