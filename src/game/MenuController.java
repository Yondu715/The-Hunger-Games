package src.game;

import java.io.IOException;
import java.net.URL;
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
import src.SceneSwitcher;

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
}
