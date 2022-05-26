package src.game;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import src.SceneSwitcher;

public class MenuController {

    public AnchorPane Pane;

    @FXML
    private Button btn_auto;

    @FXML
    private Button btn_rating;

    @FXML
    private Button btn_start;

    @FXML
    private Label txt_masseg;

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
            try {
                new SceneSwitcher().switchScene("\\resources\\Rating.fxml");
                Pane.getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btn_auto.setOnAction(event -> {
            try {
                new SceneSwitcher().switchScene("\\resources\\Autorization.fxml");
                Pane.getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
