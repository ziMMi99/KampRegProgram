package com.example.kampregprogram.controllers;
import com.example.kampregprogram.DBO.Game;
import com.example.kampregprogram.data.DataLayer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class GameOverviewController implements Initializable {
    @FXML
    MenuButton menuButton;
    @FXML
    private Label welcomeText;
    @FXML
    private ListView<Game> allGames;
    @FXML
    private Button switchSceneBut;
    @FXML
    private Label kampOversigtLabel;

    private static Game currentGame;

    private Stage stage;

    //When the buttom is clicked sends you to the gameReport.fxml
    public void onKamprapportClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/kampregprogram/gameReport.fxml"));
        Parent root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    //Use this to send the selected game info
    public static Game getCurrentGame(){
        return currentGame;
    }
    //Gives the functionallity to import a game report into the database. It needs to be a specific format to work
    public void importGameReport() throws FileNotFoundException {
        //FileChooser makes it possible to open the windows filechooser
        FileChooser fileChooser = new FileChooser();
        //An extension filter allows the filechooser to sort files depending for file format. in this case we want .csv files only
        FileChooser.ExtensionFilter ex1 = new FileChooser.ExtensionFilter("CSV Files", "*.csv");
        //add the extensions filter to the filechooser
        fileChooser.getExtensionFilters().add(ex1);
        //Set the title of the filechooser
        fileChooser.setTitle("Open a game report file");
        //Set the initial directory. In other words, the directory that is opened when you open the filechooser
        fileChooser.setInitialDirectory(new File("C:\\Users\\simon\\OneDrive\\Dokumenter\\KampRegProgram\\ImportFiles"));
        //Make a file instance, of the selected file choosen. "showOpenDialog" opens the windows filechooser.
        File selectedFile = fileChooser.showOpenDialog(stage);
        //Checks if there is a file selected. if true, sysout the path, and a little message
        if (selectedFile != null) {
            System.out.println("Open File");
            System.out.println(selectedFile.getPath());
        }
        //Data handling
        try {
            DataLayer data = new DataLayer();
            //Make a new fileReader, with the selected file path.
            FileReader fileReader = new FileReader(selectedFile.getPath());
            //Make a bufferedReader out of the filereader. The reason for this is, that the filereader is slow.
            //The bufferedReader has a method called "readLine()" which reads an entire line.
            //FileReader is usually only used to read characters from a file
            //BufferedReader is mainly used to read lines from a file
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            //Read the first line of the file. Which are just names of the values below. (dont want that)
            bufferedReader.readLine();
            int generatedID = 0;
            String line;
            //While there is a line being read. in other words, if the line is not empty
            while ((line = bufferedReader.readLine()) != null) {
                //Make an array of strings, split at every comma. this is because the array already seperates the values
                //with a comma. and the values in the files are comma seperated (csv),
                //Each comma seperated value, should be inserted as its own index in the array
                //Allowing the use of array[i], to get the value needed from the array
                String[] reportData = line.split(",");

                //If the length of the data is 6. Which is the length of the game row, it will run the method that inserts the game into the database
                if (reportData.length == 6) {
                    System.out.println(Arrays.toString(reportData));
                    //The method returns the generated ID.
                    generatedID = data.insertGameReportImport(reportData);
                    //Read the header for the Event log.
                    bufferedReader.readLine();
                    //Same as last if statement, this time it runs the method for inserting the event log into the database
                } else if (reportData.length == 3) {
                    //The eventlog only has 3 values. The reason for this is, even though the eventlog has 4 values (acutally 5, but id is auto increment)
                    //The value "matchID" has to be the same as the newly generatedID from game report insert. This is why the game report import method returns a generated id
                    //This generated id is passed into the insertEventImport method, effectivly linking the game, to the match event log
                    //A game report is the combination of the game, and event log.
                    System.out.println(Arrays.toString(reportData));
                    data.insertEventImport(generatedID, reportData);
                } else {
                    //Error handling, if the lenght of the data does not match.
                    //Does not check the type of values, for example if a letter is in a numbers slot, it will not catch this, resulting in an error
                    //But its is just a prototype-
                    System.out.println("Error while reading file. Error in data format: " + line);
                }
            }
            //close both bufferedReader and filereader.
            bufferedReader.close();
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //We establish a connection to the database to we can get the data we need
        DataLayer data = new DataLayer();

        //This runs selectAllFinishedGames() from DataLayer so we can get all the games that are finished
        ArrayList<Game> list = data.selectAllFinishedGames();

        //The ArrayList gets added to the ListView, so it can be showed
        allGames.getItems().addAll(list);

        //Add a listener, so we can see what game is selected
        allGames.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Game>() {
            @Override
            public void changed(ObservableValue<? extends Game> arg0, Game arg1, Game arg2) {

                currentGame = allGames.getSelectionModel().getSelectedItem();
            }
        });

        // ----------
        // NAVIGATION MENU
        // ------------
        MenuItem menuItem1 = new MenuItem("Team Overview");
        MenuItem menuItem2 = new MenuItem("Game Overview");
        MenuItem menuItem3 = new MenuItem("Game Recorder");
        MenuItem menuItem4 = new MenuItem("Create Game");

        menuButton.getItems().addAll(menuItem1, menuItem2, menuItem3, menuItem4);

        menuItem1.setOnAction(event -> {
            try {
                TeamOverviewController.switchPageToTeamOverview(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        menuItem2.setOnAction(event -> {
            try {
                TeamOverviewController.switchPageToGameOverview(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        menuItem3.setOnAction(event -> {
            try {
                TeamOverviewController.switchPageToGameChoice(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        menuItem4.setOnAction(event -> {
            try {
                TeamOverviewController.switchPageToCreateGame(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // --------------
        // END OF NAVIGATION
        //---------------
    }
}