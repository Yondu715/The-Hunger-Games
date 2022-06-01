package src.game;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
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

    public AnchorPane Pane;

    @FXML
    private Button btn_auth, btn_rating, btn_start, btn_reg, btn_sign_in;

    @FXML
    private Label txt_massage, status;

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

    DatabaseHandler dbHandler = new DatabaseHandler();
    Singleton loginHandler;

    @FXML
    void initialize(){
        startPane.setVisible(true);
        startPane.setOpacity(1);
        ratePane.setVisible(false);
        ratePane.setOpacity(1);
        authPane.setVisible(false);
        authPane.setOpacity(1);
        login_col.setCellValueFactory(new PropertyValueFactory<Player, String>("login"));
        points_col.setCellValueFactory(new PropertyValueFactory<Player, Integer>("points"));

        try {
            if (loginHandler.getCreatedInstance() != null){
                status.setText(loginHandler.getCreatedInstance().getLogin());
            }
        } catch (Exception e) {}

        btn_sign_in.setOnAction(event -> {
            String login = login_text.getText().trim();
            String password = pass_text.getText().trim();

            if (!login.equals("") && !password.equals("") && dbHandler.loginPlayer(login, password)){
                login_text.clear();
                pass_text.clear();
                status.setText(login);
                txt_massage.setText("");
                loginHandler.getInstance(login);
            } else {
                Shake playerLoginAnim = new Shake(login_text);
                Shake playerPasswordAnim = new Shake(pass_text);
                playerLoginAnim.playAnim();
                playerPasswordAnim.playAnim();
                txt_massage.setText("User not found");
            }

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
                try {
                    new SceneSwitcher().switchScene("\\resources\\game.fxml");
                    Pane.getScene().getWindow().hide();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
            try {
                new SceneSwitcher().switchScene("\\resources\\Registr.fxml");
                Pane.getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void showRating(){
        ObservableList<Player> rating = FXCollections.observableArrayList();
        rating = dbHandler.getRating();
        table.setItems(rating);
    }
}
