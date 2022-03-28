package utils;
import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class utils {
        public static HashMap<String, Scene> scenes = new HashMap<>();
        public static void load() throws IOException{
            Parent rootSplash = FXMLLoader.load(utils.class.getClassLoader().getResource(".\\resources\\splash.fxml"));
            Scene splash = new Scene(rootSplash);
            scenes.put("splash", splash);
        }
}