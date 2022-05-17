package src.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;;
import src.database.playerRating;

public class DB {

    private Connection connection = null;
    private String username;
    private String password;

    public DB(String username, String password){
        this.username = username;
        this.password = password;
    }

    public void getConnection(){
        try {
            Class.forName("org.postgresql.Driver");
            String url = "";
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.out.print("Cannot load driver");
        }
    }

    public void closeConnection(){
        try {
            if (connection != null){
                connection.close();
            }
        } catch (Exception e) {}
    }

    public ResultSet exeQuery(String query){
        ResultSet rs = null;
        getConnection();
        try {
            rs = this.connection.createStatement().executeQuery(query);
        } catch (Exception e) {
            System.out.print("Error in execute query");
        }
        closeConnection();
        return rs;
    }

    public void exeUpdate(String query){
        getConnection();
        try {
            this.connection.createStatement().executeUpdate(query);
        } catch (Exception e) {
            System.out.print("Error in update query");
        }
        closeConnection();
    }

    public ObservableList<playerRating> getAllPlayers(){
        ObservableList<playerRating> players = FXCollections.observableArrayList();
        ResultSet rs;
        String query = "select id, username, record from player, record where player.id = record.id;";
        rs = exeQuery(query);
        try {
            while(rs.next()){
                playerRating playerRating = new playerRating(rs.getInt("id"), rs.getString("username"), rs.getInt("record"));
                players.add(playerRating);
            }
        } catch (Exception e) {
            System.out.print("Error in getAllPlayers");
        }
        return players;
    }

    public void registerNewPlayer(String username, String password){
        String query = "insert into player(username, password) values(\'" + username + "\', \'" + password + "\');";
        exeUpdate(query);
    }

    public void deletePlayer(String username){
        String queryRecord = "delete from record where id=(select id from players where username = \'" + username + "\');";
        String queryPlayer = "delete from player where username = \'" + username + "\';";
        exeUpdate(queryRecord);
        exeUpdate(queryPlayer);
    }

    public boolean auth(String username, String password){
        ResultSet rs;
        String query = "select username, password from player where username = \'" + username + "\' and password = \'" + password + "\';";
        rs = exeQuery(query);
        try {
            rs.first();
            if (rs.getString("username").equals(username) && rs.getString("password").equals(password)){
                return true;
            }
        } catch (Exception e) {
            System.out.print("Auth error");
        }
        return false;
    }

    public void removeRecord(String username){
        String query = "update record set score = 0 where id=(select id from players where username = \'" + username + "\');";
        exeUpdate(query);
    }
    
}
