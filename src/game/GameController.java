package src.game;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class GameController implements Initializable {

    @FXML
    Pane gamePane;
    static Pane rootGame = new Pane();
    @FXML
    Label time, playerScore;
    @FXML
    ProgressBar playerHp;
    
    public static ArrayList<Food> foods = new ArrayList<>();
    private HashMap<KeyCode, Boolean> keys = new HashMap<>();
    Character person = new Character(30, 30);
    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long arg0) {
            update(); 
        }
    };

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        rootGame = gamePane;
        rootGame.getChildren().add(person);
        playerHp.setProgress(person.hp);
        playerScore.setText(playerScore.getText() + "0");
        time.setText("60");
        rootGame.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        rootGame.setOnKeyReleased(event -> keys.put(event.getCode(), false));
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
            rootGame.getChildren().remove(person);
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
        food_spawn();
        setHpAndScore();
    }

    public void food_spawn(){
        int random = (int)Math.floor(Math.random()*300);
        double pos_x = Math.floor(Math.random()*rootGame.getWidth());
        System.out.println(rootGame.getHeight());
        double pos_y = Math.floor(Math.random()*(rootGame.getHeight() - 150));
        if (random == 7){
            Food food = new Food(pos_x, pos_y, 7, Color.RED);
            foods.add(food);
            rootGame.getChildren().add(food);
        }        
    }

    public void setHpAndScore(){
        playerHp.setProgress(person.hp/100);
        playerScore.setText("Score: " + String.valueOf(person.score));
    }

    public void change_time(){
        time.setText("60");
        int max_time = 60;
        Thread t = new Thread(){
            public void run(){
                for (int i =0; i<max_time; i++){
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