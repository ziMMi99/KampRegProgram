package com.example.kampregprogram;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import com.example.kampregprogram.data.DataLayer;

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


    @FXML
    private TextField teamNameTextField;
    @FXML
    private TextField numberOfPlayersTextField;
    @FXML
    private TextField teamCityTextField;

    @FXML
    private void handleCreateButtonClick() {
        try {
            String teamName = teamNameTextField.getText();
            int numberOfPlayers = Integer.parseInt(numberOfPlayersTextField.getText());
            String teamCity = teamCityTextField.getText();

            Team newTeam = new Team(teamName, numberOfPlayers, 0, teamCity,1);

            DataLayer dataLayer = new DataLayer();
            dataLayer.insertIntoTeam(newTeam.getName(), newTeam.getNumberOfPlayers(), newTeam.getPoint(), newTeam.getTeamCity(), newTeam.getActive());

            System.out.println("New Team Created:\n" + newTeam.toString());


        } catch (NumberFormatException e) {

            System.out.println("Feltet ANTAL SPILLERE kan kun indeholde tal!");
        }
    }

    @FXML
    private void handleEditButtonClick() {
        Team selectedTeam = getSelectedTeam();

        if (selectedTeam != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("editTeam.fxml"));
                Parent root = loader.load();

                EditTeamController editTeamController = loader.getController();
                editTeamController.setSelectedTeam(selectedTeam);
                editTeamController.initialize();

                Stage editStage = new Stage();
                editStage.setScene(new Scene(root));
                editStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Vælg venligst et hold du vil rette fra hold-oversigten");
        }
    }

    private Team getSelectedTeam() {

        return new Team("Team 1", 11, 0, "City 1", 1);
    }


}