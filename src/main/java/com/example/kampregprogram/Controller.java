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

    private static Game currentGame;

    public void onKamprapportClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("KampRapport.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public static Game getCurrentGame(){
        return currentGame;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DataLayer data = new DataLayer();
        ArrayList<Game> list = data.selectAllFinishedGames();

        allGames.getItems().addAll(list);

        allGames.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Game>() {
            @Override
            public void changed(ObservableValue<? extends Game> arg0, Game arg1, Game arg2) {

                currentGame = allGames.getSelectionModel().getSelectedItem();
                //System.out.println(Arrays.toString(currentFood));

            }
        });

    }
}