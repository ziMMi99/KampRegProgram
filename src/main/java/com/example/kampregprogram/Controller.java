package com.example.kampregprogram;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Label welcomeText;
    @FXML
    private ListView<Game> allGames;
    @FXML
    private Button switchSceneBut;
    @FXML
    private Label kampOversigtLabel;
    //@FXML
    //private ListView<Game> kampOversigtList;

    private final Game[] currentGame = new Game[1];


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void onKamprapportClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("KampRapport.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        KRController krController = new KRController();
        krController.changeLabel(currentGame);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DataLayer data = new DataLayer();
        ArrayList<Game> list = data.selectAllFinishedGames();
        //ArrayList<Game> kOL = new ArrayList<>(list.subList(0, 2));
        /*ArrayList<Game> kOL = null;
        for(Game game : list){
            Game subsetGame  = new Game(game.getId(), game.getHomeTeamID(), game.getHomeScore(), game.getAwayTeamID(), game.getAwayScore(), game.getMatchDate());
            subsetGame.setId(game.getId());
            subsetGame.setHomeTeamID(game.getHomeTeamID());
            subsetGame.setHomeScore(game.getHomeScore());
            subsetGame.setAwayTeamID(game.getAwayTeamID());
            subsetGame.setAwayScore(game.getAwayScore());
            subsetGame.setMatchDate(game.getMatchDate());
            if (kOL != null) {
                kOL.add(subsetGame);
            }
        }*/



        allGames.getItems().addAll(list);

        allGames.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Game>() {
            @Override
            public void changed(ObservableValue<? extends Game> arg0, Game arg1, Game arg2) {

                currentGame[0] = allGames.getSelectionModel().getSelectedItem();
                //System.out.println(Arrays.toString(currentFood));

            }
        });

    }
}