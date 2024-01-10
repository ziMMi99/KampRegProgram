package com.example.kampregprogram;

public class Team {
    private int id;
    private String name;
    private int numberOfPlayers;
    private int point;
    private String teamCity;

    // Default Constructor
    public Team(){
        this.id = 0;
        this.name = "";
        this.numberOfPlayers = 0;
        this.point = 0;
        this.teamCity = "";
    }
// Set Constructor
    public Team(int id, String name, int numberOfPlayers, int point, String teamCity) {
        this.id = id;
        this.name = name;
        this.numberOfPlayers = numberOfPlayers;
        this.point = point;
        this.teamCity = teamCity;
    }

    @Override
    public String toString() {
        return "Team {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", numberOfPlayers=" + numberOfPlayers +
                ", point=" + point +
                ", teamCity='" + teamCity + '\'' +
                '}';
    }


// Getters
    public int getId() {return id;}
    public String getName() {return name;}
    public int getNumberOfPlayers() {return numberOfPlayers;}
    public String getTeamCity() {return teamCity;}

// Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
    public void setPoint(int point) {
        this.point = point;
    }
    public void setTeamCity(String teamCity) {
        this.teamCity = teamCity;
    }

}
