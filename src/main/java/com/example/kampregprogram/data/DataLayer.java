package com.example.kampregprogram.data;

import com.example.kampregprogram.EventType;
import com.example.kampregprogram.DBO.Game;
import com.example.kampregprogram.DBO.MatchEventLog;

import com.example.kampregprogram.DBO.Team;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataLayer implements AutoCloseable {
    private Connection connection;

    //Constructor for the datalayer, which initializes the JDBC driver loader, and opens the connection to the database
    public DataLayer() {
        loadJdbcDriver();
        openConnection("MatchRegProgram");
    }
    //Loads the JDBC driver.'
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
    //Opens a connection the the database
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

            System.out.println("Could not connect to database: " + databaseName);
            System.out.println("Could not connect to the database: " + databaseName);

            System.out.println(e.getMessage());
            return false;
        }
    }

    //Updates the points of the team. Which is used when a game is finished.
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
    //Creates a team in the database, which the parameters given.
    public void insertIntoTeam (String name,int numberOfPlayers, int point, String teamCity,int active){

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
    //Gets a List of all teams sorted by points displaying the leading teams first
    public List<Team> getAllTeamsOrderByPoint () {
        List<Team> teams = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Team ORDER BY point DESC;";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Team team = new Team(
                        resultSet.getInt("id"),
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

    //Updates the team, with the team instance parameter
    public void updateTeam (Team team){
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


    //Gets all teams for logging purposes
    public ArrayList<Team> getTeamsForLog () {
        ArrayList<Team> teamList = new ArrayList<>();
        Team team = null;
        try {
            String sql = "SELECT * FROM team;";

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
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
    //Gets the id of a team by name, for logging purposes
    public int getTeamIDByNameLog (String teamName){
        int id = 0;
        try {
            String sql = "SELECT id FROM team WHERE name='" + teamName + "';";

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return id;
    }
    //Adds the log to the database
    public void addLogToDB (MatchEventLog log){
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
    //Updates teh status of a match to finished, after it has been played
    public void updateStatusToFinished ( int id){
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
    //Updates the score of the teams after the game has been played
    public void updateTeamScore ( int gameID, String teamScore,int scoreAmt){
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
    //Gets the name of a team, by id
    public String getTeamNameByID ( int id){

        try {
            String sql = "SELECT name FROM team WHERE id=" + id;

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                return name;
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "name not found";
    }
    //Inserts the created game into the database
    public void insertGameIntoDB (Game game){
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

    //This method is used to see all the finished games
    public ArrayList<Game> selectAllFinishedGames () {
        try {
            ArrayList<Game> games = new ArrayList<>();

            String selectAll = "SELECT * FROM Game WHERE finished= 1;";

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

    //This method is used to see all the unfinished games
    public ArrayList<Game> selectAllUnFinishedGames () {
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

    //This is used to see the event log of a specific team in a specific match
    public ArrayList<MatchEventLog> getTeamEventLog ( int matchID, int id){
        return getMatchEventLogByMatchIDandTeamID(matchID, id);
    }

    //This method helps other methods to search in the database and get a specific match and specific team
    private ArrayList<MatchEventLog> getMatchEventLogByMatchIDandTeamID(int matchID, int teamID){
        try {
            ArrayList<MatchEventLog> eventLogs = new ArrayList<>();

            String sql = "Select * From MatchEventLog WHERE matchID=" + matchID + " And teamID=" + teamID;

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int resTeamID = resultSet.getInt("teamID");
                int resMatchID = resultSet.getInt("matchID");
                String eventTypes = resultSet.getString("eventtype");
                EventType eventType = EventType.valueOf(eventTypes);
                int matchTime = resultSet.getInt("matchtime");

                MatchEventLog matchEventLog = new MatchEventLog(id, resTeamID, resMatchID, eventType, matchTime);

                eventLogs.add(matchEventLog);
            }
            statement.close();

            return eventLogs;
        } catch (SQLException e) {
            System.out.println("Error: Cold not get Game");
            System.out.println(e.getMessage());

            return null;
        }
    }


    //Closes the connection
    @Override
    public void close() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Database connection closed");
        }
    }
}

