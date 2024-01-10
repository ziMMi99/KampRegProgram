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
        return "Game{" +
                "id=" + id +
                ", homeTeamID=" + homeTeamID +
                ", homeScore=" + homeScore +
                ", awayTeamID=" + awayTeamID +
                ", awayScore=" + awayScore +
                ", matchDate='" + matchDate + '\'' +
                '}';
    }
}
