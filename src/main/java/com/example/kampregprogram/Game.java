package com.example.kampregprogram;

public class Game {
    private int id, homeTeamID, homeScore,awayTeamID, awayScore;
    private String matchDate;

    public Game (int id, int homeTeamID, int homeScore, int awayTeamID, int awayScore, String matchDate) {
        this.id = id;
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
    public void setMatchDate(String matchDate) {
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
    public String getMatchDate() {
        return matchDate;
    }
}
