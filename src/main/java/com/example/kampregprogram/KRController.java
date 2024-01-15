package com.example.kampregprogram;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;

public class KRController implements Initializable {

    @FXML
    private Label kampRapportLabel;
    @FXML
    private Label homeTeamID, homeScore, awayTeamID, awayScore, matchDate;

    private int HTID, HS, ATID, AS;
    private Date MD;

    public void changeLabel(Game[] id){
        HTID = id[0].getHomeTeamID();
        HS = id[0].getHomeScore();
        ATID = id[0].getAwayTeamID();
        AS = id[0].getAwayScore();
        MD = id[0].getMatchDate();

        System.out.println(id[0]);
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

        setLabel(HTID, HS, ATID, AS, MD);
    }
}
