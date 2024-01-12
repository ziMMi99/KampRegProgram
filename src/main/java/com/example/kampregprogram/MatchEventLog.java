package com.example.kampregprogram;
import java.sql.Timestamp;

public class MatchEventLog {
    private int id, teamID, MatchID;
    private EventType eventType;
    private int matchTime;


    //
    public MatchEventLog(int id, int teamID, int MatchID, EventType eventType, int matchTime){
        this.id = id;
        this.teamID = teamID;
        this.MatchID = MatchID;
        this.eventType = eventType;
        this.matchTime = matchTime;
    }
    public MatchEventLog(int teamID, int MatchID, EventType eventType, int matchTime){
        this.teamID = teamID;
        this.MatchID = MatchID;
        this.eventType = eventType;
        this.matchTime = matchTime;
    }

    //Setters
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setMatchID(int matchID) {
        MatchID = matchID;
    }
    public void setMatchTime(int matchTime) {
        this.matchTime = matchTime;
    }
    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    //Getters
    public EventType getEventType() {
        return eventType;
    }
    public int getId() {
        return id;
    }
    public int getMatchID() {
        return MatchID;
    }
    public int getTeamID() {
        return teamID;
    }
    public int getMatchTime() {
        return matchTime;
    }

    @Override
    public String toString() {
        return "MatchEventLog{" +
                "id=" + id +
                ", teamID=" + teamID +
                ", MatchID=" + MatchID +
                ", eventType=" + eventType +
                ", matchTime=" + matchTime +
                '}';
    }
}


