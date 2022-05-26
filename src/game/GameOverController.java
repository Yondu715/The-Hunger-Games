package src.game;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import src.SceneSwitcher;

public class GameOverController {

    public AnchorPane Pane;

    @FXML
    private Button btn_menu;

    @FXML
    private Button btn_rest;

    @FXML
    private Label txt_message;

    @FXML
    private Label txt_points;

    @FXML
    void initialize() {
        btn_menu.setOnAction(event -> {
            try {
                new SceneSwitcher().switchScene("\\resources\\menu.fxml");
                Pane.getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btn_rest.setOnAction(event -> {
            try {
                new SceneSwitcher().switchScene("\\resources\\game.fxml");
                Pane.getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
