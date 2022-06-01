package src.DB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;

public class DatabaseHandler extends Configs{
    Connection dbConnection;

    public Connection getDbConnection(){
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);  
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dbConnection;
    }

    public void closeConnection(){
        if (dbConnection != null){
            try {
                dbConnection.close();
            } catch (Exception e) {}            
        }
    }

    public void signUpPlayer(String login, String password){
        String insert = "INSERT INTO " + Const.PLAYER_TABLE + "(" +
                Const.PLAYER_LOGIN + "," + Const.PLAYER_PASSWORD + ")" +
                "VALUES(?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, login);
            prSt.setString(2, password);
            prSt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    public Boolean getPlayer(String login, String password){
        ResultSet resSet = null;

        String select = "SELECT * FROM " + Const.PLAYER_TABLE + " WHERE " +
                Const.PLAYER_LOGIN + "=? AND " + Const.PLAYER_PASSWORD + "=?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, login);
            prSt.setString(2, password);
            resSet = prSt.executeQuery();
            if (resSet.next()){
                closeConnection();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeConnection();
        return false;
    }

    public ObservableList<Player> getRating(){
        ObservableList<Player> rating = FXCollections.observableArrayList();
        ResultSet resSet = null;

        String select = "SELECT " + Const.PLAYER_LOGIN + " , " + Const.PLAYER_SCORE_SCORE + " FROM " + Const.PLAYER_TABLE + " INNER JOIN " + 
                        Const.PLAYER_SCORE_TABLE + " ON " +  Const.PLAYER_SCORE_TABLE + "." + Const.PLAYER_SCORE_ID_PLAYER + "=" + Const.PLAYER_TABLE + "." + Const.PLAYER_ID;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
            while(resSet.next()){
                Player player = new Player(resSet.getString("player.login"), resSet.getInt("player_score.score"));
                rating.add(player);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeConnection();
        return rating;
    }

    public boolean loginPlayer(String login, String password){
        try {
            if (getPlayer(login, password)){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void playerScore(String login, int score) {

        ResultSet resSet = null;
        Integer new_score = null;
        Integer id_player = null;

        String selectPlayer = "(SELECT " + Const.PLAYER_ID + " FROM " + Const.PLAYER_TABLE + " WHERE " +
                Const.PLAYER_LOGIN + "=?)";
        
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(selectPlayer);
            prSt.setString(1, login);
            resSet = prSt.executeQuery();
            if (resSet.next()) {
                id_player = resSet.getInt(Const.PLAYER_ID);
            } else {
                closeConnection();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();;
        }
        
        String selectPlayer_score = "SELECT " + Const.PLAYER_SCORE_SCORE + " FROM " + Const.PLAYER_SCORE_TABLE + " WHERE " +
                Const.PLAYER_SCORE_ID_PLAYER + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(selectPlayer_score);
            prSt.setInt(1, id_player);
            prSt.executeQuery();
            resSet = prSt.executeQuery();
            if (resSet.next()) {
                new_score = resSet.getInt(Const.PLAYER_SCORE_SCORE);
            }
        } catch (Exception e) {
            e.printStackTrace();;
        }

        if (score> new_score) {
            String update = "UPDATE " + Const.PLAYER_SCORE_TABLE + " SET " +
                            Const.PLAYER_SCORE_SCORE + "=? WHERE " + Const.PLAYER_SCORE_ID_PLAYER + "=?";
            try {
                PreparedStatement prSt = getDbConnection().prepareStatement(update);
                prSt.setInt(1, score);
                prSt.setInt(2, id_player);
                prSt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        closeConnection();
    }
}
