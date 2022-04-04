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
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class GameController implements Initializable {

    @FXML
    AnchorPane gamePane;
    static AnchorPane rootGame = new AnchorPane();
    @FXML
    Rectangle topFrame;
    @FXML
    Label time, playerScore;
    @FXML
    ProgressBar playerHp;
    
    public static ArrayList<Food> foods = new ArrayList<>();
    private HashMap<KeyCode, Boolean> keys = new HashMap<>();
    Character person = new Character(50, 50);
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
            person.moveY(-0.68);
            person.moveX(-0.68);
        }
        else if (isPressed(KeyCode.UP) && (isPressed(KeyCode.RIGHT))){
            person.moveY(-0.7);
            person.moveX(0.7);
        }
        else if (isPressed(KeyCode.DOWN) && (isPressed(KeyCode.LEFT))){
            person.moveY(0.7);
            person.moveX(-0.7);
        }
        else if (isPressed(KeyCode.DOWN) && (isPressed(KeyCode.RIGHT))){
            person.moveY(0.7);
            person.moveX(0.7);
        }
        else if (isPressed(KeyCode.UP)){
            person.moveY(-1.0);
        }
        else if (isPressed(KeyCode.DOWN)){
            person.moveY(1.0);
        }
        else if (isPressed(KeyCode.LEFT)){
            person.moveX(-1.0);
        }
        else if (isPressed(KeyCode.RIGHT)){
            person.moveX(1.0);
        }
    }

    public void check_alive_players(){
        person.get_damage();
        if (!person.isAlive()){
            rootGame.getChildren().remove(person);
            timer.stop();
        }
    }

    public void check_time(){
        if (time.getText().equals("0")){
            timer.stop();
        }
    }

    public void check_collision(){
        double y = person.getPosY();
        double x = person.getPosX();
        if (y > topFrame.getHeight() && y < (gamePane.getHeight() - person.getCharacterHeight()) && x > 0 && x < (gamePane.getWidth()) - person.getCharacterWidth()) checkMove();
        else if (y <= topFrame.getHeight()) person.moveY(0.1);
        else if (y >= (gamePane.getHeight() - person.getCharacterHeight())) person.moveY(-0.1);
        else if (x >= gamePane.getWidth() - person.getCharacterWidth()) person.moveX(-0.1);
        else if (x <= 0) person.moveX(0.1);
    }

    public void update(){
        check_collision();
        check_alive_players();
        check_time();
        food_spawn();
        setHpAndScore();
    }

    public void food_spawn(){
        int random = (int)Math.floor(Math.random()*300);
        double pos_x = Math.floor(Math.random() * (gamePane.getWidth() - 2 * gamePane.getPadding().getRight()) + gamePane.getPadding().getLeft());
        double pos_y = Math.floor(Math.random() * (gamePane.getHeight() - gamePane.getPadding().getBottom() - gamePane.getPadding().getTop()) + gamePane.getPadding().getTop());
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