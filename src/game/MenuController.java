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
import src.DB.DatabaseHandler;
import src.DB.Player;
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
    private TableColumn<?, ?> login;
    @FXML
    private TableColumn<?, ?> points;

    @FXML
    void initialize(){
        startPane.setVisible(true);
        startPane.setOpacity(1);
        ratePane.setVisible(false);
        ratePane.setOpacity(1);
        authPane.setVisible(false);
        authPane.setOpacity(1);
        btn_sign_in.setOnAction(event -> {
            String login = login_text.getText().trim();
            String password = pass_text.getText().trim();

            if (!login.equals("") && !password.equals("")){
                loginPlayer(login, password);
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
            ratePane.setVisible(true);
            authPane.setVisible(false);
        });
        btn_auth.setOnAction(event -> {
            startPane.setVisible(false);
            ratePane.setVisible(false);
            authPane.setVisible(true);
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

    private void loginPlayer(String login, String password){
        try {
            DatabaseHandler dbHandler = new DatabaseHandler();
            if (dbHandler.getPlayer(login, password)){
                System.out.print("Success");
            } else {
                Shake playerLoginAnim = new Shake(login_text);
                Shake playerPasswordAnim = new Shake(pass_text);
                playerLoginAnim.playAnim();
                playerPasswordAnim.playAnim();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }
}
