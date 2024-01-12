package com.example.kampregprogram;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class GameRecorderController {

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

    /*
    Kan lave en overtime knap
    */
    @FXML
    Label stopWatchFXID;
    int seconds = 0;
    //length of match in seconds
    int matchLength = 3600;
    //how many milliseconds a second takes in the clock
    int timeSpeed = 1000;
    boolean timerStarted = false;
    Timer myRepeatingTimer = new Timer();

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
        //startTimer();
    }

    //Event handlers
    public void eventLogGoalHome(ActionEvent event) {

        //MatchEventLog HomeGoalLog = new MatchEventLog(game.getHomeTeamID,game.getID, EventType.goal,seconds);
    }
    public void eventLogSuspensionHome(ActionEvent event) {

    }
    public void eventLogFreeThrowHome(ActionEvent event) {

    }
    public void eventLogGoalAway(ActionEvent event) {

    }
    public void eventLogSuspensionAway(ActionEvent event) {

    }
    public void eventLogFreeThrowAway(ActionEvent event) {

    }

}