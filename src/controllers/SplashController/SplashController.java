package src.controllers.SplashController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import src.SceneSwitcher;

public class SplashController implements Initializable{
    @FXML
    Pane splashPane;
    Pane gamePane;
    @FXML
    ProgressBar progress;
    @FXML
    ImageView image;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        splash();  
    }

    private void splash(){
        new Thread(){
            public void run(){
                try {
                    Thread.sleep(1000);
                    for (int i = 1; i <= 100; i++){
                        final double loaded = i;
                        Thread.sleep(25);
                        Platform.runLater(new Runnable() {
                            public void run(){
                                progress.setProgress(loaded/100);
                            }
                        });
                    }
                } catch (Exception e) {}
                Platform.runLater(new Runnable() {
                    public void run(){
                        try {
                            new SceneSwitcher().switchScene("\\resources\\game.fxml");
                            splashPane.getScene().getWindow().hide();
                        } catch (IOException e1) {} 
                    }
                });
            }
        }.start();;
    }
}
