package src.game;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.swing.Action;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import src.BD.DatebaseHandler;
import src.BD.Player;
import src.SceneSwitcher;
import src.resources.animations.Shake;

public class MenuController {

    public AnchorPane Pane;

    @FXML
    private Button btn_auth, btn_rating, btn_start, btn_reg, btn_sign_in;

    @FXML
    private Label txt_messeg;

    @FXML
    private Pane startPane, authPane, ratePane;

    @FXML
    private TextField login_text;

    @FXML
    private PasswordField pass_text;

    @FXML
    private TableView<?> table;

    @FXML
    private TableColumn<?, ?> level;
    @FXML
    private TableColumn<?, ?> place;
    @FXML
    private TableColumn<?, ?> point;

    @FXML
    void initialize(){
        btn_sign_in.setOnAction(event -> {
            String loginText = login_text.getText().trim();
            String passwordText = pass_text.getText().trim();

            if (!loginText.equals("") && !passwordText.equals("")){
                try {
                    loginPlayer(loginText, passwordText);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.print("Login or password is empty");
            }
        });

        btn_start.setOnAction(event -> {
            try {
                new SceneSwitcher().switchScene("\\resources\\game.fxml");
                Pane.getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btn_rating.setOnAction(event -> {
            startPane.setVisible(false);
            startPane.setOpacity(0);
            ratePane.setVisible(true);
            ratePane.setOpacity(1);
            authPane.setVisible(false);
            authPane.setOpacity(0);
        });
        btn_auth.setOnAction(event -> {
            startPane.setVisible(false);
            startPane.setOpacity(0);
            ratePane.setVisible(false);
            ratePane.setOpacity(0);
            authPane.setVisible(true);
            authPane.setOpacity(1);
        });
        btn_reg.setOnAction(event -> {
            try {
                new SceneSwitcher().switchScene("\\resources\\Registr.fxml");
                Pane.getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void loginPlayer(String loginText, String passwordText)
            throws SQLException, ClassNotFoundException {
        DatebaseHandler dbHandler = new DatebaseHandler();
        Player player = new Player();
        player.setLogin(loginText);
        player.setPassword(passwordText);
        ResultSet result = dbHandler.getPlayer(player);

        int counter = 0;
        while (result.next()) {
            counter++;
        }
        if (counter >= 1)
        {
            System.out.print("Success");
        }
        else {
            Shake playerLoginAnim = new Shake(login_text);
            Shake playerPasswordAnim = new Shake(pass_text);
            playerLoginAnim.playAnim();
            playerPasswordAnim.playAnim();
        }
    }
}
