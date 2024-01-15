package com.example.kampregprogram.data;

import com.example.kampregprogram.Team;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataLayer implements AutoCloseable {
    private Connection connection;

    public DataLayer() {
        loadJdbcDriver();
        openConnection("MatchRegProgram");
    }

    private boolean loadJdbcDriver() {
        try {
            System.out.println("Loading JDBC driver...");

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            System.out.println("JDBC driver loaded");

            return true;
        } catch (ClassNotFoundException e) {
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
                            "integratedSecurity=true;" +
                            "trustServerCertificate=true;";

            System.out.println(connectionString);

            System.out.println("Connecting to the database...");

            connection = DriverManager.getConnection(connectionString);

            System.out.println("Connected to the database");

            return true;
        } catch (SQLException e) {
            System.out.println("Could not connect to the database: " + databaseName);
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void insertIntoTeam(String name, int numberOfPlayers, int point, String teamCity, int active) {
        try {
            String sql = "INSERT INTO Team (name, numberOfPlayers, point, teamCity, active) VALUES (?, ?, ?, ?, ?);";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, name);
            statement.setInt(2, numberOfPlayers);
            statement.setInt(3, point);
            statement.setString(4, teamCity);
            statement.setInt(5, active);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Connection to the database failed.");
        }
    }

    public List<Team> getAllTeams() {
        List<Team> teams = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Team ORDER BY point DESC;";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Team team = new Team(
                        resultSet.getInt("id"),  // Include the id
                        resultSet.getString("name"),
                        resultSet.getInt("numberOfPlayers"),
                        resultSet.getInt("point"),
                        resultSet.getString("teamCity"),
                        resultSet.getInt("active")
                );
                teams.add(team);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teams;
    }


    public void updateTeam(Team team) {
        try {
            String sql = "UPDATE Team SET name=?, numberOfPlayers=?, point=?, teamCity=?, active=? WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, team.getName());
            statement.setInt(2, team.getNumberOfPlayers());
            statement.setInt(3, team.getPoint());
            statement.setString(4, team.getTeamCity());
            statement.setInt(5, team.getActive());
            statement.setInt(6, team.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void close() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Database connection closed");
        }
    }
}
