package com.example.kampregprogram;

import java.sql.Date;
import java.sql.Timestamp;

public class Game {
    private int id, homeTeamID, homeScore,awayTeamID, awayScore, finished;
    private Date matchDate;


    public Game (int id, int homeTeamID, int homeScore, int awayTeamID, int awayScore, Date matchDate, int finished) {
        this.id = id;
        this.homeTeamID = homeTeamID;
        this.homeScore = homeScore;
        this.awayTeamID = awayTeamID;
        this.awayScore = awayScore;
        this.matchDate = matchDate;
    }
    public Game (int homeTeamID, int homeScore, int awayTeamID, int awayScore, Date matchDate, int finished) {
        this.homeTeamID = homeTeamID;
        this.homeScore = homeScore;
        this.awayTeamID = awayTeamID;
        this.awayScore = awayScore;
        this.matchDate = matchDate;
    }

    @Override
    public String toString() {
        return "Game {" +
                "id=" + id +
                ", homeTeamID=" + homeTeamID +
                ", homeScore=" + homeScore +
                ", awayTeamID=" + awayTeamID +
                ", awayScore=" + awayScore +
                ", matchDate='" + matchDate + '\'' +
                '}';
    }

    //Setters
    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }
    public void setAwayTeamID(int awayTeamID) {
        this.awayTeamID = awayTeamID;
    }
    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }
    public void setHomeTeamID(int homeTeamID) {
        this.homeTeamID = homeTeamID;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    //Getters
    public int getAwayScore() {
        return awayScore;
    }
    public int getAwayTeamID() {
        return awayTeamID;
    }
    public int getHomeScore() {
        return homeScore;
    }
    public int getHomeTeamID() {
        return homeTeamID;
    }
    public int getId() {
        return id;
    }
    public Date getMatchDate() {
        return matchDate;
    }
}
