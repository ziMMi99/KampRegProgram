package com.example.kampregprogram;

import com.example.kampregprogram.data.DataLayer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editTeam.fxml"));
        Parent root = fxmlLoader.load();


        EditTeamController editTeamController = fxmlLoader.getController();


        Team selectedTeam = getSelectedTeam();


        editTeamController.setSelectedTeam(selectedTeam);
        editTeamController.initialize();

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        DataLayer data = new DataLayer();

        launch();

        // MatchEventLog eventLog = new MatchEventLog(24,10,2,EventType.goal,new Timestamp(System.currentTimeMillis()));
        // System.out.println(eventLog);
    }


    private Team getSelectedTeam() {
        // For demonstration purposes, returning a dummy team
        return new Team("Team 1", 11, 0, "City 1", 1);
    }
}
