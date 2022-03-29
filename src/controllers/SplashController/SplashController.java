package src.controllers.SplashController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import src.SceneSwitcher;

public class SplashController implements Initializable{
    @FXML
    ProgressBar progress;
    @FXML
    ImageView image;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        image.setImage(new Image(getClass().getResourceAsStream("..\\..\\resources\\index.jpg")));
        new SplashScreen().start();  
    }
    
    class SplashScreen extends Thread{
        public void run(){
            try {
                for (int i = 1; i <= 100; i++){
                    final double loaded = i;
                    Thread.sleep(25);
                    Platform.runLater(new Runnable() {
                        public void run(){
                            progress.setProgress(loaded/100);
                        }
                    });
                }
                Platform.runLater(new Runnable() {
                    public void run() {
                        try {
                            new SceneSwitcher().switchScene("\\resources\\game.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        } 
                    }
                });
                
            } catch (InterruptedException e) { e.printStackTrace();}
        }
    }
    
}
