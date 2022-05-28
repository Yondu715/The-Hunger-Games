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
            if (resSet.next()) return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        return rating;
    }

    public void PlayerScore(String login, int score) {

        ResultSet resSet = null;

        String select = "(SELECT " + Const.PLAYER_ID + " FROM " + Const.PLAYER_TABLE + " WHERE " +
                Const.PLAYER_LOGIN + "='" + login + "')";

        String select2 = "SELECT " + Const.PLAYER_SCORE_SCORE + " FROM " + Const.PLAYER_SCORE_TABLE + " WHERE " +
                Const.PLAYER_SCORE_ID_PLAYER + "=?";

        Integer new_score = null;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select2);
            prSt.setString(1, select);
            prSt.executeQuery();
            resSet = prSt.executeQuery();
            if (resSet.next()) {
                new_score = resSet.getInt(Const.PLAYER_SCORE_SCORE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (Integer.parseInt(String.valueOf(score)) > Integer.parseInt(String.valueOf(new_score))) {
            String insert = "INSERT INTO " + Const.PLAYER_SCORE_TABLE + "(" +
                    Const.PLAYER_SCORE_ID_PLAYER + "," + Const.PLAYER_SCORE_SCORE + ")" +
                    "VALUES(?,?)";
            try {
                PreparedStatement prSt = getDbConnection().prepareStatement(insert);
                prSt.setString(1, select);
                prSt.setString(2, String.valueOf(score));
                prSt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
