package src;
import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SceneSwitcher {
    public void switchScene(String scenePath) throws IOException{
        AnchorPane gamePane = FXMLLoader.load(getClass().getResource(scenePath));
        Stage stage = new Stage();
        Scene scene = new Scene(gamePane);
        scene.getRoot().requestFocus();
        stage.setScene(scene);
        stage.getIcons().add(new Image("resources\\icons\\burger.png"));
        stage.setTitle("The Hunger Games");
        stage.centerOnScreen();
        stage.setMinHeight(530);
        stage.setMinWidth(969);
        if (scenePath == "\\resources\\game.fxml"){
            stage.setOnCloseRequest(event -> {
                Platform.exit();
                System.exit(0);
            });
        }
        if (scenePath == "\\resources\\menu.fxml") {
            stage.setOnCloseRequest(event -> {
                Platform.exit();
                System.exit(0);
            });
        }
        if (scenePath == "\\resources\\Registr.fxml") {
            stage.setOnCloseRequest(event -> {
                Platform.exit();
                System.exit(0);
            });
        }
        if (scenePath == "\\resources\\GameOver.fxml") {
            stage.setOnCloseRequest(event -> {
                Platform.exit();
                System.exit(0);
            });
        }
        stage.show();
    }
}
