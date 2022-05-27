package src.BD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DatebaseHandler extends Configs{
    Connection dbConnection;

    public Connection getDbConnection()
            throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/" + dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    public void signUpPlayer(Player player)
            throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Const.PLAYER_TABLE + "(" +
                Const.PLAYER_LOGIN + "," + Const.PLAYER_PASSWORD + ")" +
                "VALUES(?,?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        prSt.setString(1, player.getLogin());
        prSt.setString(2, player.getPassword());

        prSt.executeUpdate();
    }

    public ResultSet getPlayer(Player player)
            throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;

        String select = "SELECT * FROM " + Const.PLAYER_TABLE + " WHERE " +
                Const.PLAYER_LOGIN + "=? AND " + Const.PLAYER_PASSWORD + "=?";

        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setString(1, player.getLogin());
        prSt.setString(2, player.getPassword());

        resSet = prSt.executeQuery();

        return resSet;
    }
}
