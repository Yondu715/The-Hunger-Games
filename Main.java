
import javafx.application.Application;
import javafx.stage.Stage;
import utils.utils;

public class Main extends Application{
    public static void main(String[] args) {		
        Application.launch(args);	
    }
     
    @Override
    public void start(Stage stage) throws Exception {
        utils.load();
        stage.setScene(utils.scenes.get("splash"));
        stage.setTitle("Game");
        stage.show();
    }
}