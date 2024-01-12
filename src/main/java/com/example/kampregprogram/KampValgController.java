package com.example.kampregprogram;

import com.example.kampregprogram.data.DataLayer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class KampValgController implements Initializable {

    @FXML
    ListView<String> unFinishedGames = new ListView<>();
    DataLayer data = new DataLayer();
    ArrayList<Game> gameList = data.selectAllUnFinishedGames();
    ArrayList<String> listViewGames = new ArrayList<>();
    String selectedGame;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Game game : gameList) {
            listViewGames.add(data.getTeamNameByID(game.getHomeTeamID()) + " vs " + data.getTeamNameByID(game.getAwayTeamID()));
        }

        unFinishedGames.getItems().addAll(listViewGames);
        System.out.println(unFinishedGames.getItems());
        unFinishedGames.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String game, String t1) {
                selectedGame = unFinishedGames.getSelectionModel().getSelectedItem();
            }
        });
    }
}
