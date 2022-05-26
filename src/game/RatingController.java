package src.game;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import src.SceneSwitcher;

public class RatingController {

    public AnchorPane Pane;

    @FXML
    private Button btn_back;

    @FXML
    private TableColumn<?, ?> level;

    @FXML
    private TableColumn<?, ?> place;

    @FXML
    private TableColumn<?, ?> point;

    @FXML
    private TableView<?> table;

    @FXML
    void initialize() {
        btn_back.setOnAction(event -> {
            try {
                new SceneSwitcher().switchScene("\\resources\\menu.fxml");
                Pane.getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
