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
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class KampValgController implements Initializable {

    @FXML
    ListView<Game> unFinishedGames = new ListView<>();
    DataLayer data = new DataLayer();
    ArrayList<Game> gameList = data.selectAllUnFinishedGames();
    static Game selectedGame;

    public void startMatch(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gameRecorder.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static Game getSelectedGame() {
        return selectedGame;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        unFinishedGames.getItems().addAll(gameList);
        System.out.println(unFinishedGames.getItems());
        unFinishedGames.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Game>() {
            @Override
            public void changed(ObservableValue<? extends Game> observableValue, Game game, Game t1) {
                selectedGame = unFinishedGames.getSelectionModel().getSelectedItem();
                System.out.println("" + selectedGame);
            }
        });
    }
}
