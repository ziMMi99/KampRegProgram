package com.example.kampregprogram.data;
import com.example.kampregprogram.Game;

import java.sql.*;
import java.util.ArrayList;

public class DataLayer {
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
                            "integratedSecurity=true;" + // bruger Windows credentials
                            "trustServerCertificate=true;"; // afht. SSL forbindelse

            System.out.println(connectionString);

            System.out.println("Connecting to database...");

            connection = DriverManager.getConnection(connectionString);

            System.out.println("Connected to database");

            return true;
        } catch (SQLException e) {
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

    public ArrayList<Game> selectAllGames() {
        try {
            ArrayList<Game> games = new ArrayList<>();

            String selectAll = "SELECT * FROM Game;";

            //System.out.println(selectAll);

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(selectAll);

            // iteration starter 'before first'
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int homeTeamID = resultSet.getInt("homeTeamID");
                int homeScore = resultSet.getInt("homeScore");
                int awayTeamID = resultSet.getInt("awayTeamID");
                int awayScore = resultSet.getInt("awayScore");
                Date matchDate = resultSet.getDate("matchDate");

                Game game = new Game(id, homeTeamID, homeScore, awayTeamID, awayScore,matchDate);

                games.add(game);
            }

            statement.close();

            return games;
        } catch (SQLException e) {
            System.out.println("Error: Cold not get Game");
            System.out.println(e.getMessage());

            return null;
        }
    }
}
