package src.game;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import src.DB.DatabaseHandler;
import src.DB.Player;
import src.SceneSwitcher;
import src.resources.animations.Shake;

public class MenuController {

    @FXML
    private Button btn_auth, btn_rating, btn_start, btn_reg, btn_sign_in;

    @FXML
    private Label txt_massage, status;

    @FXML
    private AnchorPane menuPane;

    @FXML
    private Pane startPane, authPane, ratePane;

    @FXML
    private TextField login_text;

    @FXML
    private PasswordField pass_text;

    @FXML
    private TableView<Player> table;

    @FXML
    private TableColumn<Player, String> login_col;
    @FXML
    private TableColumn<Player, Integer> points_col;

    DatabaseHandler dbHandler = DatabaseHandler.getInstance();
    Singleton loginHandler = Singleton.getCreatedInstance();

    @FXML
    void initialize(){
        try {
            File soundFile = new File("resources\\music\\1.wav");
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.setFramePosition(0);
            clip.start();	
        } catch (Exception e) {}

        startPane.setVisible(true);
        startPane.setOpacity(1);
        ratePane.setVisible(false);
        ratePane.setOpacity(1);
        authPane.setVisible(false);
        authPane.setOpacity(1);
        login_col.setCellValueFactory(new PropertyValueFactory<Player, String>("login"));
        points_col.setCellValueFactory(new PropertyValueFactory<Player, Integer>("points"));

        if (loginHandler.getCreatedInstance() != null){
            status.setText(loginHandler.getLogin());
        }

        btn_sign_in.setOnAction(event -> {
            String login = login_text.getText().trim();
            String password = pass_text.getText().trim();

            new Thread(
                () ->  {
                    if (dbHandler.loginPlayer(login, password)){
                        if (loginHandler.getCreatedInstance() != null){
                            loginHandler.setLogin(login);
                        } else {
                            loginHandler = Singleton.getInstance(login);
                        }
                        Platform.runLater(new Runnable() {
                            public void run(){
                                login_text.clear();
                                pass_text.clear();
                                status.setText(login);
                                txt_massage.setText("");
                            }
                        });
                    } else if (!login.equals("") && !password.equals("") && !dbHandler.loginPlayer(login, password)){
                        Platform.runLater(new Runnable() {
                            public void run(){
                                Shake playerLoginAnim = new Shake(login_text);
                                Shake playerPasswordAnim = new Shake(pass_text);
                                playerLoginAnim.playAnim();
                                playerPasswordAnim.playAnim();
                                txt_massage.setText("User not found");
                            }
                        });
                    }
                }
            ).start();

            if (login.equals("") && !password.equals("")) {
                Shake playerLoginAnim = new Shake(login_text);
                playerLoginAnim.playAnim();
                txt_massage.setText("Login is empty");
            }
            else if (!login.equals("") && password.equals("")) {
                Shake playerPasswordAnim = new Shake(pass_text);
                playerPasswordAnim.playAnim();
                txt_massage.setText("Password is empty");
            }
            else if (login.equals("") && password.equals("")){
                Shake playerLoginAnim = new Shake(login_text);
                Shake playerPasswordAnim = new Shake(pass_text);
                playerLoginAnim.playAnim();
                playerPasswordAnim.playAnim();
                txt_massage.setText("Login and password is empty");
            }
        });

        btn_start.setOnAction(event -> {
            if (status.getText().equals("* No connection ")){
                startPane.setVisible(false);
                ratePane.setVisible(false);
                authPane.setVisible(true);
            } else {
                new SceneSwitcher().switchScene("\\resources\\game.fxml");
                menuPane.getScene().getWindow().hide();
            } 
        });

        btn_rating.setOnAction(event -> {
            startPane.setVisible(false);
            ratePane.setVisible(true);
            authPane.setVisible(false);
            showRating();
        });

        btn_auth.setOnAction(event -> {
            startPane.setVisible(false);
            ratePane.setVisible(false);
            authPane.setVisible(true);
        });
        
        btn_reg.setOnAction(event -> {
            new SceneSwitcher().switchScene("\\resources\\Registr.fxml");
            menuPane.getScene().getWindow().hide();
        });
    }

    public void showRating(){
        new Thread(){
            public void run(){
                final ObservableList<Player> rating = dbHandler.getRating();
                Platform.runLater(new Runnable() {
                    public void run(){
                        table.setItems(rating);
                    }
                });
            }
        }.start();
    }
}
