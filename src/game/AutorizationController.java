package src.game;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import src.SceneSwitcher;

public class AutorizationController {

    public AnchorPane Pane;

    @FXML
    Button btn_back_auto;

    @FXML
    Button btn_enter_auto;

    @FXML
    Button btn_reg;

    @FXML
    TextField login_text;

    @FXML
    PasswordField pass_text;

    @FXML
    void initialize() {
        btn_reg.setOnAction(event -> {
            try {
                new SceneSwitcher().switchScene("\\resources\\Registr.fxml");
                Pane.getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btn_back_auto.setOnAction(event -> {
            try {
                new SceneSwitcher().switchScene("\\resources\\menu.fxml");
                Pane.getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
