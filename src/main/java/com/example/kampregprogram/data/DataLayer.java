package com.example.kampregprogram.data;

import com.example.kampregprogram.EventType;
import com.example.kampregprogram.DBO.Game;
import com.example.kampregprogram.DBO.MatchEventLog;

import com.example.kampregprogram.DBO.Team;

import java.io.BufferedWriter;
import java.io.FileWriter;
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

            statement.close();

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

            statement.close();

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

            statement.close();

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

            statement.close();

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

            statement.close();

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

            statement.close();

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

            statement.close();

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

            statement.close();

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

            statement.close();

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

            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "name not found";
    }
    //Inserts the created game into the database
    public void insertGameIntoDB (Game game){
        try {
            String sql = "INSERT INTO Game (homeTeamID, homeScore, awayTeamID, awayScore, matchDate, finished) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, game.getHomeTeamID());
            statement.setInt(2, 0);
            statement.setInt(3, game.getAwayTeamID());
            statement.setInt(4, 0);
            statement.setDate(5, game.getMatchDate());
            statement.setInt(6, 0);

            statement.executeUpdate();

            statement.close();

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
    //Takes 2 resultsets and writes them to a csv file
    public void exportGameReportToCsv(int gameID) {
        //The name and location where the file will be saved. "ExportFiles" is the folder in which it will be saved to
        //"GameReport" is the name of the file "+gameId" is to differentiate the files from each other. ".csv" is the file format
        //CSV = comma-separated values
        String csvFilePath = "ExportFiles/GameReport" + gameID + ".csv";

        try {
            //Gets the Data from the database, where the data matches the game id parameter
            String sqlGame = "SELECT * FROM Game WHERE id=" + gameID + ";";
            String sqlEventLog = "SELECT * FROM MatchEventLog WHERE matchID=" + gameID + ";";

            Statement statement = connection.createStatement();
            //Execute only the first sql statement. It's important to not execute both statements here, since this will close the first resultset
            //Resulting in an error "Resultset is closed". It will only work if you execute the second sql statement after the first while loop.
            ResultSet resultSetGame = statement.executeQuery(sqlGame);
            //A bufferedWriter is used like a Stringbuilder. It gives the ability to make changes to a file, until you close it
            //A new instance of FileWriter is made inside the bufferedwriter.
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));
            //Make a line in the top, displaying different value names
            fileWriter.write("id, homeTeamID, homeScore, awayTeamID, awayScore, matchDate, finished");
            //Creates a new line
            fileWriter.newLine();

            while (resultSetGame.next()) {
                int id = resultSetGame.getInt("id");
                int homeTeamID = resultSetGame.getInt("homeTeamID");
                int homeScore = resultSetGame.getInt("awayTeamID");
                int awayScore = resultSetGame.getInt("awayScore");
                Date matchDate = resultSetGame.getDate("matchDate");
                int finished = resultSetGame.getInt("finished");
                //A fancy way to make a string. %s is a placeholder for text. All it does is make a string
                //and fill the "%s" placeholders with the values after. It's an alternative to string concatenation
                String gameline = String.format("%s,%s,%s,%s,%s,%s", id, homeTeamID, homeScore, awayScore, matchDate, finished);
                //Write the line that just got made
                fileWriter.write(gameline);
                fileWriter.newLine();
            }

            //makes a header for the MatchLogEvent data
            fileWriter.newLine();
            fileWriter.write("EventID, MatchTime, TeamID, MatchID, EventType");
            fileWriter.newLine();
            //execute the second sql statement after the first while loop. As descriped above
            ResultSet resultSetEventLog = statement.executeQuery(sqlEventLog);

            while (resultSetEventLog.next()) {
                int id = resultSetEventLog.getInt("id");
                int matchTime = resultSetEventLog.getInt("matchTime");
                int teamID = resultSetEventLog.getInt("teamID");
                int matchID = resultSetEventLog.getInt("matchID");
                String eventTypes = resultSetEventLog.getString("eventtype");
                EventType eventType = EventType.valueOf(eventTypes);

                //Same procedure as before, just with some other information
                String eventLogLine = String.format("%s,%s,%s,%s,%s", id, matchTime, teamID, matchID, eventType);
                fileWriter.newLine();
                fileWriter.write(eventLogLine);
            }

            //close the statement, and the fileWriter
            statement.close();
            fileWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportLeagueStandingCsv() {
        //Gets the date today, and formats it to a readable date
        Date date = new Date(System.currentTimeMillis());

        String csvFilePath = "ExportFiles/LeagueStanding" + date + ".csv";

        try {
            //Select all teams by points. (Highest first)
            String sql = "SELECT * FROM Team ORDER BY point DESC;";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));

            fileWriter.write("id, name, numberOfPlayers, point, teamCity, active");
            fileWriter.newLine();

            while (resultSet.next()) {
                int id =resultSet.getInt("id");
                String name = resultSet.getString("name");
                int numberOfPlayers = resultSet.getInt("numberOfPlayers");
                int point = resultSet.getInt("point");
                String teamCity = resultSet.getString("teamCity");
                int active = resultSet.getInt("active");

                String teamline = String.format("%s,%s,%s,%s,%s,%s", id, name, numberOfPlayers, point, teamCity, active);

                fileWriter.write(teamline);
                fileWriter.newLine();
            }

            statement.close();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
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

