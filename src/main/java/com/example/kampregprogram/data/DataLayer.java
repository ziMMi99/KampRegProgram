package com.example.kampregprogram.data;
import java.sql.*;
import java.util.ArrayList;
import com.example.kampregprogram.Team;

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

    public void insertIntoTeam(String teamName, int numberOfPlayers, int point, String teamCity, int active) {
        try {
            String sql = "INSERT INTO Team (teamName, numberOfPlayers, point, teamCity, active) VALUES (?, ?, ?, ?, ?);";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, teamName);
            statement.setInt(2, numberOfPlayers);
            statement.setInt(3, 0);
            statement.setString(4, teamCity);
            statement.setInt(5, active);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Connection to the database failed.");
        }
    }

    public void updateTeam(Team updatedTeam, int teamId) {
        try {
            String sql = "UPDATE Team SET teamName = ?, numberOfPlayers = ?, teamCity = ? WHERE id = ?;";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, updatedTeam.getName());
            statement.setInt(2, updatedTeam.getNumberOfPlayers());
            statement.setString(3, updatedTeam.getTeamCity());
            statement.setInt(4, teamId);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Connection to the database failed.");
        }
    }

    public Team getTeamDetails(int teamId) throws SQLException {
        Team team = null;

        try {
            String sql = "SELECT * FROM Team WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, teamId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String teamName = resultSet.getString("teamName");
                int numberOfPlayers = resultSet.getInt("numberOfPlayers");
                String teamCity = resultSet.getString("teamCity");

                team = new Team(teamName, numberOfPlayers, 0, teamCity, 1);
            }
        } catch (SQLException e) {
            throw new SQLException("Error fetching team details from the database", e);
        }

        return team;
    }





}
