package com.example.kampregprogram;

import com.example.kampregprogram.data.DataLayer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;

public class KRController implements Initializable {

    @FXML
    private Label kampRapportLabel;
    @FXML
    private Label homeTeamID, homeScore, awayTeamID, awayScore, matchDate;
    @FXML
    private ListView<MatchEventLog> allHomeTeam;
    @FXML
    private ListView<MatchEventLog> allAwayTeam;

    private int HTID, HS, ATID, AS, MID;
    private Date MD;
    private Game selectedGame = Controller.getCurrentGame();

    public void changeLabel(){
        MID = selectedGame.getId();
        HTID = selectedGame.getHomeTeamID();
        HS = selectedGame.getHomeScore();
        ATID = selectedGame.getAwayTeamID();
        AS = selectedGame.getAwayScore();
        MD = selectedGame.getMatchDate();

        setLabel(HTID, HS, ATID, AS, MD);

        //System.out.println(selectedGame);
    }

    public void setLabel(int HTID, int HS, int ATID, int AS, Date MD){
        homeTeamID.setText("Home Team ID: " + HTID);
        homeScore.setText("Home Team Score: " + HS);
        awayTeamID.setText("Away Team ID: " + ATID);
        awayScore.setText("Away Team Score: " + AS);
        matchDate.setText("Match Date: " + MD);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeLabel();

        DataLayer data = new DataLayer();
        ArrayList<MatchEventLog> listOfHomeTeam = data.getTeamEventLog(MID, HTID);
        //System.out.println(listOfHomeTeam);

        allHomeTeam.getItems().addAll(listOfHomeTeam);

        ArrayList<MatchEventLog> listOfAwayTeam = data.getTeamEventLog(MID, ATID);

        allAwayTeam.getItems().addAll(listOfAwayTeam);


    }
}
