package src.game;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
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
    Label clock, playerScore;
    @FXML
    ProgressBar playerHp;

    Thread t;
    Food removeFood = null;
    public static ArrayList<Food> foods = new ArrayList<>();
    private HashMap<KeyCode, Boolean> keys = new HashMap<>();
    Character person = new Character(50, 50);
    AnimationTimer timer;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        rootGame = gamePane;
        rootGame.getChildren().add(person);
        playerHp.setProgress(person.getHp());
        rootGame.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        rootGame.setOnKeyReleased(event -> keys.put(event.getCode(), false));
        rootGame.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observableValue, Bounds bounds, Bounds newBounds){
                topFrame.setWidth(rootGame.getWidth());
                if (bounds.getHeight() != 0) {
                    topFrame.setHeight(topFrame.getHeight() + (newBounds.getHeight() - bounds.getHeight()) * 0.01);
                }
                clock.setLayoutX(topFrame.getWidth() / 2);
                clock.setMinHeight(topFrame.getHeight());
                playerHp.setMinWidth(topFrame.getWidth() * 0.175);
                playerHp.setMinHeight(topFrame.getHeight() / 2);
                playerScore.setLayoutX(playerHp.getMinWidth() + 25);
                playerScore.setMinHeight(topFrame.getHeight());
            }
        });
        timer = new AnimationTimer() {        
            @Override
            public void handle(long arg0) {
                update(); 
            }
        };
        timer.start();
        change_time(); 
    }

    public void update(){
        check_move();
        check_alive_players();
        check_time();
        food_spawn();
        setHpAndScore();
        isFoodEat();
    }

    public boolean isPressed(KeyCode key){
        return keys.getOrDefault(key, false);
    }

    public void check_move(){
        double y = person.getPosY();
        double x = person.getPosX();
        if (isPressed(KeyCode.UP) && (isPressed(KeyCode.LEFT)) && (y > topFrame.getHeight()) && (x > 0)){
            person.moveY(-1.);
            person.moveX(-1.);
        }
        else if (isPressed(KeyCode.UP) && (isPressed(KeyCode.RIGHT)) && (y > topFrame.getHeight()) && (x < (gamePane.getWidth() - person.getCharacterWidth()))){
            person.moveY(-1.);
            person.moveX(1.);
        }
        else if (isPressed(KeyCode.DOWN) && (isPressed(KeyCode.LEFT)) && (y < (gamePane.getHeight() - person.getCharacterHeight())) && (x > 0) ){
            person.moveY(1.);
            person.moveX(-1.);
        }
        else if (isPressed(KeyCode.DOWN) && (isPressed(KeyCode.RIGHT)) && (y < (gamePane.getHeight() - person.getCharacterHeight())) && (x < (gamePane.getWidth() - person.getCharacterWidth()))){
            person.moveY(1.);
            person.moveX(1.);
        }
        else if (isPressed(KeyCode.UP) && (y > topFrame.getHeight())){
            person.moveY(-1.4);
        }
        else if (isPressed(KeyCode.DOWN) && (y < (gamePane.getHeight() - person.getCharacterHeight()))){
            person.moveY(1.4);
        }
        else if (isPressed(KeyCode.LEFT) && (x > 0)){
            person.moveX(-1.4);
        }
        else if (isPressed(KeyCode.RIGHT) && (x < (gamePane.getWidth() - person.getCharacterWidth()))){
            person.moveX(1.4);
        }
    }

    public void check_alive_players(){
        person.get_damage();
        if (!person.isAlive()){
            rootGame.getChildren().remove(person);
            timer.stop();
            t.interrupt();
        }
    }

    public void check_time(){
        if (clock.getText().equals("0")){
            timer.stop();
        }
    }

    public void food_spawn(){
        int random = (int)Math.floor(Math.random() * 10);
        double pos_x = Math.floor(Math.random() * (gamePane.getWidth() - 30) + 15);
        double pos_y = Math.floor(Math.random() * (gamePane.getHeight() - 50) + 45);
        if (random == 7){
            Food food = new Food(pos_x, pos_y, 7, Color.RED);
            foods.add(food);
            rootGame.getChildren().add(food);
        }        
    }

    public void setHpAndScore(){
        playerHp.setProgress(person.getHp()/100);
        playerScore.setText("Score: " + String.valueOf(person.getScore()));
    }

    public void change_time(){
        clock.setText("120");
        int max_time = 120;
        t = new Thread(){
            public void run(){
                for (int i = 0; i < max_time; i++){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {}
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            int seconds = Integer.parseInt(clock.getText());
                            seconds--;
                            clock.setText(String.valueOf(seconds));
                        }
                    });
                }
            }
        };
        t.start();
    }

    public void isFoodEat(){
        foods.forEach((food) -> {
            if (person.getBoundsInParent().intersects(food.getBoundsInParent())){
                person.setHp(person.getHp() + food.getFoodValue());
                if (person.getHp() > 100) person.setHp(100);
                person.setScore(person.getScore() + 1);
                removeFood = food;
            }
        });
        foods.remove(removeFood);
        rootGame.getChildren().remove(removeFood);
    }
    
}