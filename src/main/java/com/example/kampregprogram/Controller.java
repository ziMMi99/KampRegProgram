package com.example.kampregprogram;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML
    private Label welcomeText;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToKampRapport(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("KampRapport.fxml"));
        System.out.println("heh");
        Parent root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}