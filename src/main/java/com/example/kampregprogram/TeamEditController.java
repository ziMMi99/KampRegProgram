package com.example.kampregprogram;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import com.example.kampregprogram.data.DataLayer;
import java.io.IOException;
import javafx.scene.Scene;


public class TeamEditController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField numberOfPlayersField;

    @FXML
    private TextField pointField;

    @FXML
    private TextField teamCityField;

    @FXML
    private TextField activeField;

    @FXML
    private Button saveButton;

    private Team selectedTeam;
    private DataLayer dataLayer;

    public void initialize() {

        if (selectedTeam != null) {
            nameField.setText(selectedTeam.getName());
            numberOfPlayersField.setText(String.valueOf(selectedTeam.getNumberOfPlayers()));
            pointField.setText(String.valueOf(selectedTeam.getPoint()));
            teamCityField.setText(selectedTeam.getTeamCity());
            activeField.setText(String.valueOf(selectedTeam.getActive()));
        }
    }

    public static void showTeamEditPage(Team team, DataLayer dataLayer) {
        try {
            FXMLLoader loader = new FXMLLoader(TeamEditController.class.getResource("TeamEdit.fxml"));
            Parent root = loader.load();

            TeamEditController controller = loader.getController();
            controller.selectedTeam = team;
            controller.dataLayer = dataLayer;
            controller.initialize();

            Stage stage = new Stage();
            stage.setTitle("Edit Team");
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
            selectedTeam.setPoint(Integer.parseInt(pointField.getText()));
            selectedTeam.setTeamCity(teamCityField.getText());
            selectedTeam.setActive(Integer.parseInt(activeField.getText()));

            dataLayer.updateTeam(selectedTeam);

            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        }
    }






}
