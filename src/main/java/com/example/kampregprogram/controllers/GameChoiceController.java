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
import java.util.Optional;
import java.util.ResourceBundle;

public class GameChoiceController implements Initializable {

    @FXML
    private MenuButton menuButton;
    @FXML
    private ListView<Game> unFinishedGames = new ListView<>();
    private DataLayer data = new DataLayer();
    //An arrayList of all games in the database which have not been played
    private ArrayList<Game> gameList = data.selectAllUnFinishedGames();
    static Game selectedGame;

    //loads up the game recorder, when the "start kamp" button is pressed
    public void startMatch(ActionEvent event) throws IOException {

        if (unFinishedGames.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Vælg en kamp");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isEmpty()) {
                System.out.println("Alert closed");
            } else if (result.get() == ButtonType.OK) {
                return;
            }
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/kampregprogram/gameRecorder.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    //Returns the selected game in the listview
    public static Game getSelectedGame() {
        return selectedGame;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Adds all the unplayed games to a listview
        unFinishedGames.getItems().addAll(gameList);
        //System.out.println(unFinishedGames.getItems());
        //Adds a changelistener to the listview, which register when an element in the list is selected
        unFinishedGames.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Game>() {
            @Override
            public void changed(ObservableValue<? extends Game> observableValue, Game game, Game t1) {
                //Assings the selected element to a variable
                selectedGame = unFinishedGames.getSelectionModel().getSelectedItem();
                //System.out.println("" + selectedGame);
            }
        });

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
