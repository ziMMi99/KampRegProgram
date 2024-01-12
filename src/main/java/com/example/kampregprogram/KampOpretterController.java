package com.example.kampregprogram;

import com.example.kampregprogram.data.DataLayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;

import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class KampOpretterController implements Initializable {

    @FXML
    ChoiceBox<String> HomeTeamChoiceBox;
    @FXML
    ChoiceBox<String> AwayTeamChoiceBox;
    @FXML
    DatePicker GameDate;

    public void OnCreateGame(ActionEvent event){
        try {
            if (HomeTeamChoiceBox.getValue().equals(AwayTeamChoiceBox.getValue())) {
                System.out.println("DeT KA DU IK");
                return;
            }

            DataLayer data = new DataLayer();
            Date gameDate = Date.valueOf(GameDate.getValue());
            System.out.println(gameDate);
            String homeTeam = HomeTeamChoiceBox.getValue();
            System.out.println(homeTeam);
            String awayTeam = AwayTeamChoiceBox.getValue();
            System.out.println(awayTeam);

            int homeTeamID = data.getTeamIDByNameLog(homeTeam);
            int awayTeamID = data.getTeamIDByNameLog(awayTeam);

            Game game = new Game(homeTeamID, 0, awayTeamID, 0, gameDate);
            System.out.println(game);

            data.insertGameIntoDB(game);
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("Ingen hold valgt");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataLayer data = new DataLayer();
        ArrayList<Team> teams = data.getTeamsForLog();
        ArrayList<String> teamNames = new ArrayList<>();

        for (Team team : teams) {
            String teamName = team.getName();
            teamNames.add(teamName);
        }

        HomeTeamChoiceBox.getItems().addAll(teamNames);
        AwayTeamChoiceBox.getItems().addAll(teamNames);

    }
}
