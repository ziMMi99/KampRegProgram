package com.example.kampregprogram;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Arrays;

public class KRController {

    @FXML
    private Label kampRapportLabel;
    @FXML
    private Label homeTeamID, homeScore, awayTeamID, awayScore, matchDate;

    public void changeLabel(Game[] id){
        String currentGame = Integer.toString(id[0].getHomeTeamID());
        System.out.println(currentGame);
        //homeTeamID.setText(currentGame);

    }

}
