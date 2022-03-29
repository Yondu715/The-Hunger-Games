package src;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneSwitcher {
    public void switchScene(String scenePath) throws IOException{
        Parent parent = FXMLLoader.load(SceneSwitcher.class.getResource(scenePath));
        Scene scene = new Scene(parent);
        Stage stage = Main.rootStage;
        stage.setScene(scene);
        stage.show();
    }
}
