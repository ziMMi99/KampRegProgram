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
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.util.Callback;

public class TeamOverviewController {

    @FXML
    private ListView<Team> teamListView;

    @FXML
    private Button editButton;

    private ObservableList<Team> teamList = FXCollections.observableArrayList();

    private DataLayer dataLayer;

    public void initialize() {
        dataLayer = new DataLayer();

        List<Team> teams = dataLayer.getAllTeams();
        teamList.addAll(teams);

        teamListView.setItems(teamList);

        teamListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Team> call(ListView<Team> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Team team, boolean empty) {
                        super.updateItem(team, empty);

                        if (empty || team == null) {
                            setText(null);
                            setStyle("");
                        } else {

                            setText(String.format("Holdnavn: %s -- Point: %d -- By: %s",
                                    team.getName(), team.getPoint(), team.getTeamCity()));

                            int index = getIndex();
                            if (index == 0) {
                                setStyle("-fx-background-color: gold;");
                            } else if (index == 1) {
                                setStyle("-fx-background-color: silver;");
                            } else if (index == 2) {
                                setStyle("-fx-background-color: #cd7f32;");
                            } else {
                                setStyle("");
                            }
                        }
                    }
                };
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
            createTeamStage.setTitle("Opret Hold");
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


    @FXML
    private void editSelectedTeam() {
        Team selectedTeam = teamListView.getSelectionModel().getSelectedItem();
        if (selectedTeam != null) {
            openTeamEditPage(selectedTeam);
        }
    }



    public void updateTeamList() {
        List<Team> teams = dataLayer.getAllTeams();
        teamList.clear();
        teamList.addAll(teams);
        teamListView.setItems(teamList);
    }




}
