package com.example.kampregprogram;

public class Team {
    private int id;
    private String name;
    private int numberOfPlayers;
    private int point;
    private String teamCity;
    private int active;


// Set Constructor
    public Team(int id, String name, int numberOfPlayers, int point, String teamCity, int active) {
        this.id = id;
        this.name = name;
        this.numberOfPlayers = numberOfPlayers;
        this.point = point;
        this.teamCity = teamCity;
        this.active = active;
    }

    public Team(String name, int numberOfPlayers, int point, String teamCity, int active) {
        this.name = name;
        this.numberOfPlayers = numberOfPlayers;
        this.point = point;
        this.teamCity = teamCity;
        this.active = active;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", numberOfPlayers=" + numberOfPlayers +
                ", point=" + point +
                ", teamCity='" + teamCity + '\'' +
                ", active=" + active +
                '}';
    }

    // Getters
    public int getId() {return id;}
    public String getName() {return name;}
    public int getNumberOfPlayers() {return numberOfPlayers;}
    public String getTeamCity() {return teamCity;}
    public int getPoint() {
        return point;
    }
    public int getStatus() {return active;}

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
    public void setStatus(int status){
        this.active = status;
    }

}
