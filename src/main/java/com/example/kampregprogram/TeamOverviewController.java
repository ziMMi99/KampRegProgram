package com.example.kampregprogram;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import com.example.kampregprogram.data.DataLayer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class TeamOverviewController {

    @FXML
    private ListView<Team> teamListView;

    private ObservableList<Team> teamList = FXCollections.observableArrayList();

    private DataLayer dataLayer;

    public void initialize() {
        dataLayer = new DataLayer();

        List<Team> teams = dataLayer.getAllTeams();
        teamList.addAll(teams);

        teamListView.setItems(teamList);

        teamListView.setOnMouseClicked(event -> {
            Team selectedTeam = teamListView.getSelectionModel().getSelectedItem();
            if (selectedTeam != null) {
                openTeamEditPage(selectedTeam);
            }
        });
    }

    private void openTeamEditPage(Team team) {
        TeamEditController.showTeamEditPage(team, dataLayer);
    }

    @FXML
    private void openCreateTeamView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateTeam.fxml"));
            Parent root = loader.load();

            Stage createTeamStage = new Stage();
            createTeamStage.setTitle("Create Team");
            createTeamStage.initModality(Modality.WINDOW_MODAL);
            createTeamStage.initOwner(teamListView.getScene().getWindow());

            Scene scene = new Scene(root);
            createTeamStage.setScene(scene);

            createTeamStage.showAndWait();

            updateTeamList();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateTeamList() {
        List<Team> teams = dataLayer.getAllTeams();
        teamList.clear();
        teamList.addAll(teams);
        teamListView.setItems(teamList);
    }




}
