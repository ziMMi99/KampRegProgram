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


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
/*
    public void kampOversigt(){

        HBox hbox;
        DataLayer data = new DataLayer();
        ArrayList<Game> list = data.selectAllGames();

        for (Game game : list) {
            //System.out.println(game);

            hbox = new HBox();
            Label hboxLabel = new Label(" Home teams score " + game.getHomeScore() + " Away teams score " + game.getAwayScore() + " Match date " + game.getMatchDate());

            hbox.getChildren().add(hboxLabel);
            //oversigtVBox.getChildren().add(hbox);
        }

    }
*/
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void onKamprapportClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("KampRapport.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //kampOversigt();
        DataLayer data = new DataLayer();
        ArrayList<Game> list = data.selectAllGames();
        final Game[] currentFood = new Game[1];

        allGames.getItems().addAll(list);

        allGames.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Game>() {
            @Override
            public void changed(ObservableValue<? extends Game> arg0, Game arg1, Game arg2) {

                currentFood[0] = allGames.getSelectionModel().getSelectedItem();

            }
        });

    }
}