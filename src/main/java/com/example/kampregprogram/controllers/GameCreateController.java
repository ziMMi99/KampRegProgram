package com.example.kampregprogram.controllers;

import com.example.kampregprogram.DBO.Game;
import com.example.kampregprogram.DBO.Team;
import com.example.kampregprogram.data.DataLayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class GameCreateController implements Initializable {
    //FXML
    @FXML
    MenuButton menuButton;
    @FXML
    ChoiceBox<String> HomeTeamChoiceBox;
    @FXML
    ChoiceBox<String> AwayTeamChoiceBox;
    @FXML
    DatePicker GameDate;

    public void OnCreateGame(ActionEvent event){
        try {
            //Checks if the same team is chosen in both choiceboxes. Returns out of the method if true
            if (HomeTeamChoiceBox.getValue().equals(AwayTeamChoiceBox.getValue())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Du kan ikke vælge det samme hold 2 gange");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isEmpty()) {
                    System.out.println("Alert closed");
                } else if (result.get() == ButtonType.OK) {
                }
                return;
            }

            DataLayer data = new DataLayer();

            //Takes info given by the user
            Date gameDate = Date.valueOf(GameDate.getValue());
            String homeTeam = HomeTeamChoiceBox.getValue();
            String awayTeam = AwayTeamChoiceBox.getValue();
            int homeTeamID = data.getTeamIDByNameLog(homeTeam);
            int awayTeamID = data.getTeamIDByNameLog(awayTeam);
            //makes a game from the given info
            Game game = new Game(homeTeamID, 0, awayTeamID, 0, gameDate, 0);
            //System.out.println(game);
            //Inserts the newly created game into the database
            data.insertGameIntoDB(game);
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Ingen dato valgt");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isEmpty()) {
                System.out.println("Alert closed");
            } else if (result.get() == ButtonType.OK) {
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataLayer data = new DataLayer();
        //gets all teams in the databas
        ArrayList<Team> teams = data.getTeamsForLog();
        //List for team names
        ArrayList<String> teamNames = new ArrayList<>();

        //Takes all the team instances that are in the teams arraylist, and gets all the names
        for (Team team : teams) {
            String teamName = team.getName();
            teamNames.add(teamName);
        }
        //Then adds all the names to the choiceboxes.
        HomeTeamChoiceBox.getItems().addAll(teamNames);
        AwayTeamChoiceBox.getItems().addAll(teamNames);

        // ------------
        // NAVIGATION MENU
        // ------------
        //Creates 4 menu items, with names
        MenuItem menuItem1 = new MenuItem("Team Overview");
        MenuItem menuItem2 = new MenuItem("Game Overview");
        MenuItem menuItem3 = new MenuItem("Game Recorder");
        MenuItem menuItem4 = new MenuItem("Create Game");

        //adds all four elements to the menu button
        menuButton.getItems().addAll(menuItem1, menuItem2, menuItem3, menuItem4);

        //This method is almost identical to the next 3 methods.
        //It adds and onActionEvent to the menuitems inside the menu button, that changes scene
        menuItem1.setOnAction(event -> {
            try {
                TeamOverviewController.switchPageToTeamOverview(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        menuItem2.setOnAction(event -> {
            try {
                TeamOverviewController.switchPageToGameOverview(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        menuItem3.setOnAction(event -> {
            try {
                TeamOverviewController.switchPageToGameChoice(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        menuItem4.setOnAction(event -> {
            try {
                TeamOverviewController.switchPageToCreateGame(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        // --------------
        // END OF NAVIGATION MENU
        //---------------

    }
}
