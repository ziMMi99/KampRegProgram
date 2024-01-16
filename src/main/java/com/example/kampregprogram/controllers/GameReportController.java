package com.example.kampregprogram.controllers;

import com.example.kampregprogram.DBO.Game;
import com.example.kampregprogram.DBO.MatchEventLog;
import com.example.kampregprogram.data.DataLayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class GameReportController implements Initializable {

    @FXML
    private Label kampRapportLabel;
    @FXML
    private Label homeTeamID, homeScore, awayTeamID, awayScore, matchDate;
    @FXML
    private ListView<MatchEventLog> allHomeTeam;
    @FXML
    private ListView<MatchEventLog> allAwayTeam;
    @FXML
    private Button backButton;

    private int currentHomeTeamID, currentHomeScore, currentAwayTeamID, currentAwayScore, currentMatchID;
    private Date currentMatchDate;
    private Game selectedGame = GameOverviewController.getCurrentGame();

    //Gets the selected game from Controller and edits the labels with setlabel()
    public void changeLabel(){
        currentMatchID = selectedGame.getId();
        currentHomeTeamID = selectedGame.getHomeTeamID();
        currentHomeScore = selectedGame.getHomeScore();
        currentAwayTeamID = selectedGame.getAwayTeamID();
        currentAwayScore = selectedGame.getAwayScore();
        currentMatchDate = selectedGame.getMatchDate();

        setLabel(currentHomeTeamID, currentHomeScore, currentAwayTeamID, currentAwayScore, currentMatchDate);

        //System.out.println(selectedGame);
    }

    //This changes the name of the labels to the info of the selected game
    public void setLabel(int currentHomeTeamID, int currentHomeScore, int currentAwayTeamID, int currentAwayScore, Date currentMatchDate){
        homeTeamID.setText("Home Team ID: " + currentHomeTeamID);
        homeScore.setText("Home Team Score: " + currentHomeScore);
        awayTeamID.setText("Away Team ID: " + currentAwayTeamID);
        awayScore.setText("Away Team Score: " + currentAwayScore);
        matchDate.setText("Match Date: " + currentMatchDate);
    }

    //When the buttom is clicked sends you to the gameOverview.fxml
    public void switchBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/kampregprogram/gameOverview.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void exportGameReport() {
        DataLayer data = new DataLayer();

        data.exportGameReportToCsv(selectedGame.getId());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //This makes it so the method runs when you go onto gameReport.fxml
        changeLabel();

        //We establish a connection to the database to we can get the data we need
        DataLayer data = new DataLayer();

        //This runs getTeamEventLog() from DataLayer so we can get the list of events for the home team in a specific match
        ArrayList<MatchEventLog> listOfHomeTeam = data.getTeamEventLog(currentMatchID, currentHomeTeamID);
        //System.out.println(listOfHomeTeam);

        //The ArrayList gets added to the ListView, so it can be showed
        allHomeTeam.getItems().addAll(listOfHomeTeam);

        //Here we do same as we did for the home team, but now we have to do it for the away team
        ArrayList<MatchEventLog> listOfAwayTeam = data.getTeamEventLog(currentMatchID, currentAwayTeamID);

        //The ArrayList gets added to the ListView, so it can be showed
        allAwayTeam.getItems().addAll(listOfAwayTeam);


    }
}
