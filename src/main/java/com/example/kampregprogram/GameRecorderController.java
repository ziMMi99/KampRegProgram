package com.example.kampregprogram;

import com.example.kampregprogram.data.DataLayer;
import com.microsoft.sqlserver.jdbc.SQLServerAASEnclaveProvider;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class GameRecorderController implements Initializable {

    //Event knapper
    @FXML
    Button HomeTeamGoalBtn;
    @FXML
    Button HomeTeamSuspBtn;
    @FXML
    Button HomeTeamFreeThrowBtn;
    @FXML
    Button AwayTeamGoalBtn;
    @FXML
    Button AwayTeamSuspBtn;
    @FXML
    Button AwayTeamFreeThrowBtn;
    @FXML
    Label HomeTeamLabel;
    @FXML
    Label AwayTeamLabel;
    @FXML
    Label AwayTeamScoreLabel;
    @FXML
    Label HomeTeamScoreLabel;

    /*
    Kan lave en overtime knap
    */
    @FXML
    Label stopWatchFXID;
    int seconds = 0;
    //length of match in seconds
    int matchLength = 3600;
    //how many milliseconds a second takes in the clock
    int timeSpeed = 15;
    boolean timerStarted = false;
    Timer myRepeatingTimer = new Timer();
    DataLayer data = new DataLayer();
    Game RecordedGame = KampValgController.getSelectedGame();

    boolean isPaused = false;

    //Stopwatch Handlers
    public void startTimer() {
        if (timerStarted || isPaused) {
            return;
        }

        timerStarted = true;
        //Code inside is run once every second/1000milliseconds
        myRepeatingTimer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(!isPaused){
                        seconds++;
                        updateStopWatch(seconds);
                    }
                    if (seconds >= matchLength) {
                        myRepeatingTimer.cancel();
                        finishGame();
                    }
                });
            }
        }, 0, timeSpeed);
    }
    public void updateStopWatch(int seconds) {
        int updateSeconds = seconds;
        int updateMinutes = 0;

        //Converts seconds to seconds and minutes
        while(updateSeconds >= 60){
            updateMinutes++;
            updateSeconds -= 60;
        }

        //Formats minutes and seconds to have 0's in front when it's a single digit
        String seconds_string = String.format("%02d", updateSeconds);
        String minutes_string = String.format("%02d", updateMinutes);

        stopWatchFXID.setText("" + minutes_string + ":" + seconds_string);
    }
    public void pauseTimer() {
        if (!timerStarted) {
            return;
        }
        timerStarted = false;
        isPaused = true;
    }
    public void resumeTimer() {
        isPaused = false;
        timerStarted = true;
    }
    //Game Handlers
    public void finishGame(){
        data.updateStatusToFinished(RecordedGame.getId());
        System.out.println("Updated status");

        int pointToHome = 0;
        int pointToAway = 0;

        int homeScore = RecordedGame.getHomeScore();
        int awayScore = RecordedGame.getAwayScore();

        if (homeScore == awayScore) {
            pointToHome += 1;
            pointToAway += 1;
        } else if (homeScore > awayScore) {
            pointToHome += 3;
        } else {
            pointToAway += 3;
        }
        data.updatePoints(pointToHome, RecordedGame.getHomeTeamID());
        data.updatePoints(pointToAway, RecordedGame.getAwayTeamID());
        System.out.println("Points added");
    }
    public void setLabel(Game game) {
        String homeTeamName = data.getTeamNameByID(game.getHomeTeamID());
        String awayTeamName = data.getTeamNameByID(game.getAwayTeamID());
        HomeTeamLabel.setText(homeTeamName);
        System.out.println(HomeTeamLabel);
        AwayTeamLabel.setText(awayTeamName);
        System.out.println(AwayTeamLabel);
    }
    //Event handlers
    public void eventLogGoalHome(ActionEvent event) {
        if (seconds >= matchLength || seconds == 0) {
            System.out.println("Game is not being played");
            return;
        }
        MatchEventLog homeGoalLog = new MatchEventLog(RecordedGame.getHomeTeamID(), RecordedGame.getId(), EventType.goal, seconds);
        System.out.println(homeGoalLog);
        data.addLogToDB(homeGoalLog);
        RecordedGame.setHomeScore(RecordedGame.getHomeScore() + 1);
        System.out.println(RecordedGame.getHomeScore());
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
        System.out.println(freeThrowHome);
        data.addLogToDB(freeThrowHome);
    }
    public void eventLogGoalAway(ActionEvent event) {
        if (seconds >= matchLength || seconds == 0) {
            System.out.println("Game is not being played");
            return;
        }
        MatchEventLog awayGoalLog = new MatchEventLog(RecordedGame.getAwayTeamID(), RecordedGame.getId(), EventType.goal,seconds);
        System.out.println(awayGoalLog);
        data.addLogToDB(awayGoalLog);
        RecordedGame.setAwayScore(RecordedGame.getAwayScore()+1);
        System.out.println(RecordedGame.getAwayScore());
        data.updateTeamScore(RecordedGame.getId(), "awayScore", RecordedGame.getAwayScore());
        AwayTeamScoreLabel.setText("" + RecordedGame.getAwayScore());
    }
    public void eventLogSuspensionAway(ActionEvent event) {
        if (seconds >= matchLength || seconds == 0) {
            System.out.println("Game is not being played");
            return;
        }
        MatchEventLog suspensionAway = new MatchEventLog(RecordedGame.getAwayTeamID(), RecordedGame.getId(), EventType.suspension,seconds);
        System.out.println(suspensionAway);
        data.addLogToDB(suspensionAway);
    }
    public void eventLogFreeThrowAway(ActionEvent event) {

        if (seconds >= matchLength || seconds == 0) {
            System.out.println("Game is not being played");
            return;
        }
        MatchEventLog freeThrowAway = new MatchEventLog(RecordedGame.getAwayTeamID(), RecordedGame.getId(), EventType.freeThrow,seconds);
        System.out.println(freeThrowAway);
        data.addLogToDB(freeThrowAway);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(RecordedGame);
        setLabel(RecordedGame);
    }
}