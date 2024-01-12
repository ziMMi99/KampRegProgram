package com.example.kampregprogram.data;
import com.example.kampregprogram.Game;
import com.example.kampregprogram.Team;

import java.sql.*;
import java.util.ArrayList;

public class DataLayer {
    private Connection connection;

    public DataLayer () {
        loadJdbcDriver();
        openConnection("MatchRegProgram");
    }

    private boolean loadJdbcDriver() {
        try {
            System.out.println("Loading JDBC driver...");

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            System.out.println("JDBC driver loaded");

            return true;
        }
        catch (ClassNotFoundException e) {
            System.out.println("Could not load JDBC driver!");
            return false;
        }
    }

    private boolean openConnection(String databaseName) {
        try {
            String connectionString =
                    "jdbc:sqlserver://localhost:1433;" +
                            "instanceName=SQLEXPRESS;" +
                            "databaseName=" + databaseName + ";" +
                            "integratedSecurity=true;" + // bruger Windows credentials
                            "trustServerCertificate=true;"; // afht. SSL forbindelse

            System.out.println(connectionString);

            System.out.println("Connecting to database...");

            connection = DriverManager.getConnection(connectionString);

            System.out.println("Connected to database");

            return true;
        }
        catch (SQLException e) {
            System.out.println("Could not connect to database: " + databaseName);
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void insertIntoTest() {
        try {
            String sql = "INSERT INTO Team (teamName, numberOfPlayers, point, teamCity) VALUES (?, ?, ?, ?);";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, "Ikhuhui");
            statement.setInt(2, 3);
            statement.setInt(3, 3);
            statement.setString(4, "Ikast");

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Connection to database failed.");
        }
    }

    public ArrayList<Team> getTeamsForLog() {
        ArrayList<Team> teamList = new ArrayList<>();
        Team team = null;
        try {
            String sql = "SELECT * FROM team;";

            Statement statement = connection.createStatement();
    
            ResultSet resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {
                String name = resultSet.getString("teamName");
                String teamCity = resultSet.getString("teamCity");
                int id = resultSet.getInt("id");
                int numberOfPlayers = resultSet.getInt("numberOfPlayers");
                int points = resultSet.getInt("point");
                boolean active = resultSet.getBoolean("active");
                team = new Team(id, name, numberOfPlayers, points, teamCity, active);
                teamList.add(team);
                System.out.println(teamList);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return teamList;
    }

    public int getTeamIDByNameLog(String teamName) {
        int id = 0;
        try{
            String sql = "SELECT id FROM team WHERE teamName='" + teamName + "';";

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return id;
    }

    public void insertGameIntoDB(Game game) {
        try {
            String sql = "INSERT INTO Game (homeTeamID, homeScore, awayTeamID, awayScore, matchDate) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, game.getHomeTeamID());
            pstmt.setInt(2, 0);
            pstmt.setInt(3, game.getAwayTeamID());
            pstmt.setInt(4, 0);
            pstmt.setDate(5, game.getMatchDate());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Game getGameForLog() {
        try {

        }

        return game;
    }

}
