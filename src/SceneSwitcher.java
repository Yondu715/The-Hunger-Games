package src;
import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SceneSwitcher {
    public void switchScene(String scenePath) throws IOException{
        AnchorPane gamePane = FXMLLoader.load(getClass().getResource(scenePath));
        Stage stage = new Stage();
        Scene scene = new Scene(gamePane);
        scene.getRoot().requestFocus();
        stage.setScene(scene);
        stage.centerOnScreen();
        if (scenePath == "\\resources\\game.fxml"){
            stage.setOnCloseRequest(event -> {
                Platform.exit();
                System.exit(0);
            });
        }
        stage.show();
    }
}
