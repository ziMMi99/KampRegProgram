package com.example.kampregprogram;

import com.example.kampregprogram.data.DataLayer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Label welcomeText;
    @FXML
    private VBox oversigtVBox;
    @FXML
    private Button switchSceneBut;



    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void kampOversigt(){

        DataLayer data = new DataLayer();
        ArrayList<Game> list = data.selectAllGames();

        for (Game game : list) {
            System.out.println(game);

            HBox hbox = new HBox();
            Label hboxLabel = new Label("" + game);
            hbox.getChildren().add(hboxLabel);
            oversigtVBox.getChildren().add(hbox);
        }

    }

    public void onKamprapportClick()  {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        kampOversigt();
    }
}