package com.example.kampregprogram.data;
import com.example.kampregprogram.Game;
import com.example.kampregprogram.MatchEventLog;
import com.example.kampregprogram.Team;
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

    public void updatePoints(int points, int teamID) {
        try {
            String sql = "UPDATE Team SET point = point + ? WHERE id = " + teamID;

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, points);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Connection to database failed.");
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
                int active = resultSet.getInt("active");
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

    public void addLogToDB(MatchEventLog log){
        try {
            String sql = "INSERT INTO MatchEventLog (matchtime, teamID, matchID, eventType) VALUES (?, ?, ?, ?);";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, log.getMatchTime());
            statement.setInt(2, log.getTeamID());
            statement.setInt(3, log.getMatchID());
            statement.setString(4, log.getEventType().toString());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Connection to database failed.");
        }

    }

    public void updateStatusToFinished(int id) {
        try {
            String sql = "UPDATE Game SET finished = ? WHERE id = " + id;

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, 1);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Connection to database failed.");
        }
    }

    public void updateTeamScore(int gameID, String teamScore, int scoreAmt){
        try {
            String sql = "UPDATE Game SET " + teamScore + " = ? WHERE id = " + gameID;

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, scoreAmt);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Connection to database failed.");
        }
    }

    public String getTeamNameByID(int id){

        try{
            String sql = "SELECT teamName FROM team WHERE id=" + id;

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String name = resultSet.getString("teamName");
                return name;
            }


        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return "name not found";
    }

    public void insertGameIntoDB(Game game) {
        try {
            String sql = "INSERT INTO Game (homeTeamID, homeScore, awayTeamID, awayScore, matchDate, finished) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, game.getHomeTeamID());
            pstmt.setInt(2, 0);
            pstmt.setInt(3, game.getAwayTeamID());
            pstmt.setInt(4, 0);
            pstmt.setDate(5, game.getMatchDate());
            pstmt.setInt(6, 0);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
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
                int finished = resultSet.getInt("finished");

                Game game = new Game(id, homeTeamID, homeScore, awayTeamID, awayScore,matchDate, finished);

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

    public ArrayList<Game> selectAllUnFinishedGames() {
        try {
            ArrayList<Game> games = new ArrayList<>();

            String selectAll = "SELECT * FROM Game WHERE finished=0;";

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
                int finished = resultSet.getInt("finished");

                Game game = new Game(id, homeTeamID, homeScore, awayTeamID, awayScore, matchDate, finished);

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

