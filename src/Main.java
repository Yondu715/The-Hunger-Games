package src;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application{
    public static Stage rootStage;
    public static void main(String[] args) {		
        Application.launch(args);	
    }
     
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("\\resources\\splash.fxml"));
        Scene newScene = new Scene(root);
        newScene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(newScene);
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Game");
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.centerOnScreen();
        rootStage = primaryStage;
        primaryStage.show();
    }
}