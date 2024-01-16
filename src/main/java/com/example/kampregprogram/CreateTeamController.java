package com.example.kampregprogram;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import com.example.kampregprogram.data.DataLayer;

public class CreateTeamController {

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
            String name = teamNameTextField.getText();
            int numberOfPlayers = Integer.parseInt(numberOfPlayersTextField.getText());
            String teamCity = teamCityTextField.getText();

            Team newTeam = new Team(name, numberOfPlayers, 0, teamCity,1);

            DataLayer dataLayer = new DataLayer();
            dataLayer.insertIntoTeam(newTeam.getName(), newTeam.getNumberOfPlayers(), newTeam.getPoint(), newTeam.getTeamCity(), newTeam.getActive());

            System.out.println("New Team Created:\n" + newTeam.toString());

            teamCityTextField.clear();
            teamNameTextField.clear();
            numberOfPlayersTextField.clear();

            Stage currentStage = (Stage) teamNameTextField.getScene().getWindow();
            currentStage.close();

        } catch (NumberFormatException e) {
            System.out.println("Feltet ANTAL SPILLERE kan kun indeholde tal!");
        }
    }


}