package com.example.kampregprogram.controllers;

import com.example.kampregprogram.DBO.Team;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import com.example.kampregprogram.data.DataLayer;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;


public class TeamEditController {
// Declaring variables
    @FXML
    private TextField nameField;

    @FXML
    private TextField numberOfPlayersField;

    @FXML
    private TextField teamCityField;

    @FXML
    private CheckBox activeCheckBox;

    @FXML
    private Button saveButton;

    private Team selectedTeam;
    private DataLayer dataLayer;

    public void initialize() {
        //checks that a team has been selected before trying to call the get methods
        if (selectedTeam != null) {
            // assigns values to the declared variables by calling the teams get methods
            nameField.setText(selectedTeam.getName());
            numberOfPlayersField.setText(String.valueOf(selectedTeam.getNumberOfPlayers()));
            teamCityField.setText(selectedTeam.getTeamCity());
            activeCheckBox.setSelected(selectedTeam.getActive() == 1);
        }
    }

    public static void showTeamEditPage(Team team, DataLayer dataLayer) {
        // this method is called in team overview controller sending the selected team and
        try {
            FXMLLoader loader = new FXMLLoader(TeamEditController.class.getResource("/com/example/kampregprogram/teamEdit.fxml"));
            Parent root = loader.load();

            TeamEditController controller = loader.getController();
            controller.selectedTeam = team;
            controller.dataLayer = dataLayer;
            controller.initialize();

            Stage stage = new Stage();
            stage.setTitle("Ret hold");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void saveChanges() {
        if (dataLayer != null && selectedTeam != null) {
            selectedTeam.setName(nameField.getText());
            selectedTeam.setNumberOfPlayers(Integer.parseInt(numberOfPlayersField.getText()));
            selectedTeam.setTeamCity(teamCityField.getText());
            selectedTeam.setActive(activeCheckBox.isSelected() ? 1 : 0);

            dataLayer.updateTeam(selectedTeam);

            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        }
    }





}
