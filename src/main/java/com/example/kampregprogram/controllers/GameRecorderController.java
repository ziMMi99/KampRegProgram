package com.example.kampregprogram.controllers;

import com.example.kampregprogram.EventType;
import com.example.kampregprogram.DBO.Game;
import com.example.kampregprogram.DBO.MatchEventLog;
import com.example.kampregprogram.data.DataLayer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class GameRecorderController implements Initializable {

    //Event knapper
    @FXML
    private Button finishBtn;
    @FXML
    private Button HomeTeamGoalBtn;
    @FXML
    private Button HomeTeamSuspBtn;
    @FXML
    private Button HomeTeamFreeThrowBtn;
    @FXML
    private Button AwayTeamGoalBtn;
    @FXML
    private Button AwayTeamSuspBtn;
    @FXML
    private Button AwayTeamFreeThrowBtn;
    @FXML
    private Label HomeTeamLabel;
    @FXML
    private Label AwayTeamLabel;
    @FXML
    private Label AwayTeamScoreLabel;
    @FXML
    private Label HomeTeamScoreLabel;
    @FXML
    private Label stopWatchFXID;

    //tracks the match time
    private int seconds = 0;
    //length of match in seconds
    private int matchLength = 3600;
    //how many milliseconds a second takes in the clock
    private int timeSpeed = 15;
    private boolean timerStarted = false;
    private Timer myRepeatingTimer = new Timer();
    private DataLayer data = new DataLayer();
    //Retrives which game was selected in the previous page
    private Game RecordedGame = GameChoiceController.getSelectedGame();

    boolean isPaused = false;

    //Stopwatch Handlers
    public void startTimer() {
        //Checks if timer is started or paused
        if (timerStarted || isPaused) {
            return;
        }

        timerStarted = true;
        //Code inside is run once every [timeSpeed] milliseconds. So to run it once every second timeSpeed must be 1000
        myRepeatingTimer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                //Platform.runlater is necessary for the clock to work. If not present, it will crash. and give a thread error
                Platform.runLater(() -> {
                    //Only increment time when the program isn't paused
                    if(!isPaused){
                        seconds++;
                        updateStopWatch(seconds);
                    }
                    //Checks if the amout of time passed is bigger or equal to the match length
                    if (seconds >= matchLength) {
                        //if true, it will cancel the timer, run the finishGame() method, and set the finish button to be visible
                        myRepeatingTimer.cancel();
                        finishGame();
                        finishBtn.setVisible(true);
                    }
                });
                //timeSpeed is the amout of times it will run the method per second. and its set to 15 milliseconds
                //This means that 1 hour will only take 2 minutes in real time, simulating the lenght of a real match
            }
        }, 0, timeSpeed);
    }
    //Takes the amount of time passed, and uses some math to calculate minutes and seconds
    public void updateStopWatch(int seconds) {
        int updateSeconds = seconds;
        int updateMinutes = 0;

        //Reformats seconds to seconds and minutes
        while(updateSeconds >= 60){
            updateMinutes++;
            updateSeconds -= 60;
        }

        //Formats minutes and seconds to have 0's in front when it's a single digit. like this --> 01:23
        String seconds_string = String.format("%02d", updateSeconds);
        String minutes_string = String.format("%02d", updateMinutes);

        //Changes the label to the updated time.
        stopWatchFXID.setText("" + minutes_string + ":" + seconds_string);
    }
    //Pauses the timer
    public void pauseTimer() {
        if (!timerStarted) {
            return;
        }
        timerStarted = false;
        isPaused = true;
    }
    //Resumes the timer after it has been paused
    public void resumeTimer() {
        isPaused = false;
        timerStarted = true;
    }
    
    //Game Handlers
    //Finishes the game.
    public void finishGame()  {
        //Updates the status of the game, from not player to played, in the database, by changing 0 to 1
        data.updateStatusToFinished(RecordedGame.getId());
        System.out.println("Updated status");

        int pointToHome = 0;
        int pointToAway = 0;

        int homeScore = RecordedGame.getHomeScore();
        int awayScore = RecordedGame.getAwayScore();

        //Math to check who won
        if (homeScore == awayScore) {
            pointToHome += 1;
            pointToAway += 1;
        } else if (homeScore > awayScore) {
            pointToHome += 3;
        } else {
            pointToAway += 3;
        }
        //Updates the points in the database, depending on game result
        data.updatePoints(pointToHome, RecordedGame.getHomeTeamID());
        data.updatePoints(pointToAway, RecordedGame.getAwayTeamID());
        System.out.println("Points added");
    }
    //A button will appear when the timer hits the set match length. When pressed it will change the scene
    public void finishGameBtn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/kampregprogram/gameOverview.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    //Sets the team labels to the names of the teams that are playing the match
    public void setLabel(Game game) {
        String homeTeamName = data.getTeamNameByID(game.getHomeTeamID());
        String awayTeamName = data.getTeamNameByID(game.getAwayTeamID());
        HomeTeamLabel.setText(homeTeamName);
        System.out.println(HomeTeamLabel);
        AwayTeamLabel.setText(awayTeamName);
        System.out.println(AwayTeamLabel);
    }
    //Event handlers
    //These event handlers, handle all events in the game. Which are goals, suspensions and freethrows
    // The code is very similar, so the description of the first method below describes the other 5 aswell
    public void eventLogGoalHome(ActionEvent event) {
        //First it checks if the match is not started, or is already finished. if it is, it will return out of this method
        if (seconds >= matchLength || seconds == 0) {
            System.out.println("Game is not being played");
            return;
        }
        //it then creates an instance of MatchEventLog where it passes the correct data in. including The type of event, and the time of the event
        MatchEventLog homeGoalLog = new MatchEventLog(RecordedGame.getHomeTeamID(), RecordedGame.getId(), EventType.goal, seconds);
        //System.out.println(homeGoalLog);
        //the new instance is then added to the database
        data.addLogToDB(homeGoalLog);
        //Then it adds the score to the instance of the game
        RecordedGame.setHomeScore(RecordedGame.getHomeScore() + 1);
        //System.out.println(RecordedGame.getHomeScore());
        //Which it then adds to the database, and updates the score label
        data.updateTeamScore(RecordedGame.getId(), "homeScore", RecordedGame.getHomeScore());
        HomeTeamScoreLabel.setText("" + RecordedGame.getHomeScore());
    }
    public void eventLogSuspensionHome(ActionEvent event) {
        if (seconds >= matchLength || seconds == 0) {
            System.out.println("Game is not being played");
            return;
        }
        MatchEventLog suspensionHome = new MatchEventLog(RecordedGame.getHomeTeamID(), RecordedGame.getId(), EventType.suspension,seconds);
        System.out.println(suspensionHome);
        data.addLogToDB(suspensionHome);
    }
    public void eventLogFreeThrowHome(ActionEvent event) {
        if (seconds >= matchLength || seconds == 0) {
            System.out.println("Game is not being played");
            return;
        }

        MatchEventLog freeThrowHome = new MatchEventLog(RecordedGame.getHomeTeamID(), RecordedGame.getId(), EventType.freeThrow,seconds);
        //System.out.println(freeThrowHome);
        data.addLogToDB(freeThrowHome);
    }
    public void eventLogGoalAway(ActionEvent event) {
        if (seconds >= matchLength || seconds == 0) {
            System.out.println("Game is not being played");
            return;
        }
        MatchEventLog awayGoalLog = new MatchEventLog(RecordedGame.getAwayTeamID(), RecordedGame.getId(), EventType.goal,seconds);
        //System.out.println(awayGoalLog);
        data.addLogToDB(awayGoalLog);
        RecordedGame.setAwayScore(RecordedGame.getAwayScore()+1);
        //System.out.println(RecordedGame.getAwayScore());
        data.updateTeamScore(RecordedGame.getId(), "awayScore", RecordedGame.getAwayScore());
        AwayTeamScoreLabel.setText("" + RecordedGame.getAwayScore());
    }
    public void eventLogSuspensionAway(ActionEvent event) {
        if (seconds >= matchLength || seconds == 0) {
            System.out.println("Game is not being played");
            return;
        }
        MatchEventLog suspensionAway = new MatchEventLog(RecordedGame.getAwayTeamID(), RecordedGame.getId(), EventType.suspension,seconds);
        //System.out.println(suspensionAway);
        data.addLogToDB(suspensionAway);
    }
    public void eventLogFreeThrowAway(ActionEvent event) {

        if (seconds >= matchLength || seconds == 0) {
            System.out.println("Game is not being played");
            return;
        }
        MatchEventLog freeThrowAway = new MatchEventLog(RecordedGame.getAwayTeamID(), RecordedGame.getId(), EventType.freeThrow,seconds);
        //System.out.println(freeThrowAway);
        data.addLogToDB(freeThrowAway);
    }

    //This method is executed when the scene is loaded. where its hides the finish button, and sets the names of the teams in their respective label
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        finishBtn.setVisible(false);
        System.out.println(RecordedGame);
        setLabel(RecordedGame);
    }
}