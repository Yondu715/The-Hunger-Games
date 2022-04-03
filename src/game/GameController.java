package src.game;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;


public class GameController implements Initializable {

    @FXML
    Pane gamePane;
    @FXML
    Label time;

    private HashMap<KeyCode, Boolean> keys = new HashMap<>();
    Character person = new Character(10, 10);
    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long arg0) {
            update(); 
        }
    };

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        gamePane.getChildren().add(person);
        time.setText("60");
        gamePane.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        gamePane.setOnKeyReleased(event -> keys.put(event.getCode(), false));
        timer.start();
        change_time();
    }

    public boolean isPressed(KeyCode key){
        return keys.getOrDefault(key, false);
    }

    public void checkMove(){
        if (isPressed(KeyCode.UP) && (isPressed(KeyCode.LEFT))){
            person.moveY(1);
            person.moveX(-1);
        }
        else if (isPressed(KeyCode.UP) && (isPressed(KeyCode.RIGHT))){
            person.moveY(1);
            person.moveX(1);
        }
        else if (isPressed(KeyCode.DOWN) && (isPressed(KeyCode.LEFT))){
            person.moveY(-1);
            person.moveX(-1);
        }
        else if (isPressed(KeyCode.DOWN) && (isPressed(KeyCode.RIGHT))){
            person.moveY(-1);
            person.moveX(1);
        }
        else if (isPressed(KeyCode.UP)){
            person.moveY(1);
        }
        else if (isPressed(KeyCode.DOWN)){
            person.moveY(-1);
        }
        else if (isPressed(KeyCode.LEFT)){
            person.moveX(-1);
        }
        else if (isPressed(KeyCode.RIGHT)){
            person.moveX(1);
        }
    }

    public void check_alive_players(){
        person.get_damage();
        if (!person.isAlive()){
            gamePane.getChildren().remove(person);
        }
    }

    public void check_time(){
        if (time.getText().equals("0")){
            timer.stop();
        }
    }

    public void update(){
        checkMove();
        check_alive_players();
        check_time();
    }

    public void change_time(){
        time.setText("5");
        Thread t = new Thread(){
            public void run(){
                for (int i =0; i<5; i++){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {}
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            int seconds = Integer.parseInt(time.getText());
                            seconds--;
                            time.setText(String.valueOf(seconds));
                        }
                    });
                }
            }
        };
        t.start();
    }

}