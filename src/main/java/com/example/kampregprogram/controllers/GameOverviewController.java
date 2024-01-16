package com.example.kampregprogram.controllers;
import com.example.kampregprogram.DBO.Game;
import com.example.kampregprogram.data.DataLayer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameOverviewController implements Initializable {
    @FXML
    MenuButton menuButton;
    @FXML
    private Label welcomeText;
    @FXML
    private ListView<Game> allGames;
    @FXML
    private Button switchSceneBut;
    @FXML
    private Label kampOversigtLabel;

    private static Game currentGame;

    //When the buttom is clicked sends you to the gameReport.fxml
    public void onKamprapportClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/kampregprogram/gameReport.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    //Use this to send the selected game info
    public static Game getCurrentGame(){
        return currentGame;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //We establish a connection to the database to we can get the data we need
        DataLayer data = new DataLayer();

        //This runs selectAllFinishedGames() from DataLayer so we can get all the games that are finished
        ArrayList<Game> list = data.selectAllFinishedGames();

        //The ArrayList gets added to the ListView, so it can be showed
        allGames.getItems().addAll(list);

        //Add a listener, so we can see what game is selected
        allGames.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Game>() {
            @Override
            public void changed(ObservableValue<? extends Game> arg0, Game arg1, Game arg2) {

                currentGame = allGames.getSelectionModel().getSelectedItem();
            }
        });

        // ----------
        // NAVIGATION MENU
        // ------------
        MenuItem menuItem1 = new MenuItem("Team Overview");
        MenuItem menuItem2 = new MenuItem("Game Overview");
        MenuItem menuItem3 = new MenuItem("Game Recorder");
        MenuItem menuItem4 = new MenuItem("Create Game");

        menuButton.getItems().addAll(menuItem1, menuItem2, menuItem3, menuItem4);

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
        // END OF NAVIGATION
        //---------------
    }
}