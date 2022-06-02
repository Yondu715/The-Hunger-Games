package src.game;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import src.DB.DatabaseHandler;
import src.DB.Player;
import src.SceneSwitcher;
import src.resources.animations.Shake;

public class RegistrController {

    @FXML
    private AnchorPane registrPane;

    @FXML
    public Label txt_massage;

    @FXML
    private Button btn_back_reg;

    @FXML
    private Button btn_enter_reg;

    @FXML
    private TextField login_text;

    @FXML
    private PasswordField pass_rep_txt;

    @FXML
    private PasswordField pass_text;

    @FXML
    void initialize() {
        btn_enter_reg.setOnAction(event -> signUpNewPlayer());

        btn_back_reg.setOnAction(event -> {
            new SceneSwitcher().switchScene("\\resources\\menu.fxml");
            registrPane.getScene().getWindow().hide();
        });
    }

    private void signUpNewPlayer() {
        DatabaseHandler dbHandler = DatabaseHandler.getInstance();

        String login = login_text.getText();
        String password = pass_text.getText();
        String password_rep = pass_rep_txt.getText();

        if (!login.equals("") & !password.equals("") & !password_rep.equals("") & password.equals(password_rep)){
            new Thread(
                () -> dbHandler.signUpPlayer(login, password)
            ).start();
            new SceneSwitcher().switchScene("\\resources\\menu.fxml");
            registrPane.getScene().getWindow().hide();
        } else {
            Shake playerLoginAnim = new Shake(login_text);
            Shake playerPasswordAnim = new Shake(pass_text);
            Shake playerPasswordRepeatAnim = new Shake(pass_rep_txt);
            playerLoginAnim.playAnim();
            playerPasswordAnim.playAnim();
            playerPasswordRepeatAnim.playAnim();
            txt_massage.setText("Fields is empty or password don't match");
        }
    }

}
