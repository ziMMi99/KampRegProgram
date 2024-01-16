package com.example.kampregprogram.controllers;

import com.example.kampregprogram.DBO.Team;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import com.example.kampregprogram.data.DataLayer;

import java.util.Optional;

public class CreateTeamController {

// declaring variables

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField teamNameTextField;
    @FXML
    private TextField numberOfPlayersTextField;
    @FXML
    private TextField teamCityTextField;

    @FXML
    private void handleCreateButtonClick() {
        try {
            //Gets the values typed into the fields
            if (teamCityTextField.getText().isBlank() || teamNameTextField.getText().isBlank() || numberOfPlayersTextField.getText().isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Alle felter skal være udfyldt");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isEmpty()) {
                    System.out.println("Alert closed");
                } else if (result.get() == ButtonType.OK) {
                }
                return;
            }
            String name = teamNameTextField.getText();
            int numberOfPlayers = Integer.parseInt(numberOfPlayersTextField.getText());
            String teamCity = teamCityTextField.getText();
            //Creates a new team with those variables
            Team newTeam = new Team(name, numberOfPlayers, 0, teamCity,1);
            //Creates an instance of the datalayer
            DataLayer dataLayer = new DataLayer();
            //Insert the team into the database
            dataLayer.insertIntoTeam(newTeam.getName(), newTeam.getNumberOfPlayers(), newTeam.getPoint(), newTeam.getTeamCity(), newTeam.getActive());



            //Closes the stage
            Stage currentStage = (Stage) teamNameTextField.getScene().getWindow();
            currentStage.close();

        } catch (NumberFormatException e) {
            System.out.println("Feltet ANTAL SPILLERE kan kun indeholde tal!");
        } catch (NullPointerException e) {
            System.out.println("Alle felter skal være udfyldt!");
        }
    }


}