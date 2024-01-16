package com.example.kampregprogram.controllers;

import java.util.List;
import com.example.kampregprogram.DBO.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.kampregprogram.data.DataLayer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class TeamOverviewController {

// Declaring variables
    @FXML
    MenuButton menuButton;
    @FXML
    private ListView<Team> teamListView;
    @FXML
    private Button editButton;

    private ObservableList<Team> teamList = FXCollections.observableArrayList();

    private DataLayer dataLayer;


    public void initialize() {
        // Creates an instance of the database connection / data-layer class
        dataLayer = new DataLayer();
        // List of instances of teams - fetched with the sql method getAllTeams in the datalayer
        List<Team> teams = dataLayer.getAllTeamsOrderByPoint();
        teamList.addAll(teams);
        teamListView.setItems(teamList);

        // ----------
        // Navigation
        // ------------
        MenuItem menuItem1 = new MenuItem("Team Overview");
        MenuItem menuItem2 = new MenuItem("Game Overview");
        MenuItem menuItem3 = new MenuItem("Game Recorder");
        MenuItem menuItem4 = new MenuItem("Create Game");

        menuButton.getItems().addAll(menuItem1, menuItem2, menuItem3, menuItem4);

        menuItem1.setOnAction(event -> {
            try {
                switchPageToTeamOverview(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        menuItem2.setOnAction(event -> {
            try {
                switchPageToGameOverview(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        menuItem3.setOnAction(event -> {
            try {
                switchPageToGameChoice(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        menuItem4.setOnAction(event -> {
            try {
                switchPageToCreateGame(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void switchPageToTeamOverview(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(TeamOverviewController.class.getResource("/com/example/kampregprogram/teamOverview.fxml"));
        Parent root = loader.load();

        // Get the source of the event (which is the MenuItem)
        MenuItem menuItem = (MenuItem) event.getSource();

        // Get the MenuButton that contains the MenuItem
        MenuButton menuButton = (MenuButton) menuItem.getParentPopup().getOwnerNode();

        // Get the Stage from the MenuButton
        Stage stage = (Stage) menuButton.getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void switchPageToGameOverview(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(TeamOverviewController.class.getResource("/com/example/kampregprogram/gameOverview.fxml"));
        Parent root = loader.load();

        // Get the source of the event (which is the MenuItem)
        MenuItem menuItem = (MenuItem) event.getSource();

        // Get the MenuButton that contains the MenuItem
        MenuButton menuButton = (MenuButton) menuItem.getParentPopup().getOwnerNode();

        // Get the Stage from the MenuButton
        Stage stage = (Stage) menuButton.getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void switchPageToGameChoice(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(TeamOverviewController.class.getResource("/com/example/kampregprogram/gameChoice.fxml"));
        Parent root = loader.load();

        // Get the source of the event (which is the MenuItem)
        MenuItem menuItem = (MenuItem) event.getSource();

        // Get the MenuButton that contains the MenuItem
        MenuButton menuButton = (MenuButton) menuItem.getParentPopup().getOwnerNode();

        // Get the Stage from the MenuButton
        Stage stage = (Stage) menuButton.getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void switchPageToCreateGame(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(TeamOverviewController.class.getResource("/com/example/kampregprogram/gameCreate.fxml"));
        Parent root = loader.load();

        // Get the source of the event (which is the MenuItem)
        MenuItem menuItem = (MenuItem) event.getSource();

        // Get the MenuButton that contains the MenuItem
        MenuButton menuButton = (MenuButton) menuItem.getParentPopup().getOwnerNode();

        // Get the Stage from the MenuButton
        Stage stage = (Stage) menuButton.getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    // --------------
    // END OF NAVIGATION MENU
    //---------------

    // this method is used by editSelectedTeam() where the selected team is fetched
    // Opens the edit team dialogue sending the selected team as parameter
    private void openTeamEditPage(Team team) {
        // Calls team edit controller - showteameditpage method sending the selected team as parameter
        TeamEditController.showTeamEditPage(team, dataLayer);
    }

    @FXML
    private void openCreateTeamView() {
        try {
            //When the create team button is pressed - the create team fxml is used for team creation form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/kampregprogram/teamCreate.fxml"));
            Parent root = loader.load();
            Stage createTeamStage = new Stage();
            createTeamStage.setTitle("Opret Hold");

            // WINDOW MODAL is used to generate a new window in which the new scene is displayed
            createTeamStage.initModality(Modality.WINDOW_MODAL);
            createTeamStage.initOwner(teamListView.getScene().getWindow());

            Scene scene = new Scene(root);
            createTeamStage.setScene(scene);

            /*
            show and wait method is a method that waits for the window to be closed before updating the team list.
            This is to display the new team in the existing window when created.
             */

            createTeamStage.showAndWait();

            updateTeamList();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//when the edit button is pressed the selected team is passed as parameter to the openteameditpage method
    @FXML
    private void editSelectedTeam() {
        Team selectedTeam = teamListView.getSelectionModel().getSelectedItem();
        if (selectedTeam != null) {
            openTeamEditPage(selectedTeam);
        }
    }


    /*
    This method refreshes the team list by calling the sql query again - clearing the list and reappending the teams.
    It is used in the openCreateTeamView method after the show and wait.
    */
    public void updateTeamList() {
        List<Team> teams = dataLayer.getAllTeamsOrderByPoint();
        teamList.clear();
        teamList.addAll(teams);
        teamListView.setItems(teamList);
    }





}
