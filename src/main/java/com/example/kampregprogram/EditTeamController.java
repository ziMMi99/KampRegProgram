package com.example.kampregprogram;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import com.example.kampregprogram.data.DataLayer;

import java.io.IOException;
import java.sql.SQLException;

public class EditTeamController {
    @FXML
    private TextField teamNameTextField;
    @FXML
    private TextField numberOfPlayersTextField;
    @FXML
    private TextField teamCityTextField;

    private Team selectedTeam;
    private DataLayer dataLayer;

    public void initialize() {
        dataLayer = new DataLayer();
        fetchTeamDetails();
    }

    private void fetchTeamDetails() {
        try {
            selectedTeam = dataLayer.getTeamDetails(1);
            teamNameTextField.setText(selectedTeam.getName());
            numberOfPlayersTextField.setText(Integer.toString(selectedTeam.getNumberOfPlayers()));
            teamCityTextField.setText(selectedTeam.getTeamCity());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateButtonClick() {
        try {

            String updatedTeamName = teamNameTextField.getText();
            int updatedNumberOfPlayers = Integer.parseInt(numberOfPlayersTextField.getText());
            String updatedTeamCity = teamCityTextField.getText();


            selectedTeam.setName(updatedTeamName);
            selectedTeam.setNumberOfPlayers(updatedNumberOfPlayers);
            selectedTeam.setTeamCity(updatedTeamCity);


            dataLayer.updateTeam(selectedTeam, 1);


            teamNameTextField.getScene().getWindow().hide();
        } catch (NumberFormatException e) {
            System.out.println("Feltet ANTAL SPILLERE kan kun indeholde tal!");
        }
    }

    public void setSelectedTeam(Team selectedTeam) {
        this.selectedTeam = selectedTeam;
    }
}
