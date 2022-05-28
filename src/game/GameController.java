package src.game;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import src.SceneSwitcher;
import src.game.client.Food;
import src.game.client.Character;;

public class GameController implements Initializable{

    @FXML
    AnchorPane gamePane;
    @FXML
    Rectangle topFrame;
    @FXML
    Label clock, playerScore;
    @FXML
    ProgressBar playerHp;

    private Thread clockThread;
    private Food removeFood = null;
    private List<Paint> playerColors = Arrays.asList(Color.BLACK, Color.BLUE, Color.GREEN);
    private ArrayList<Food> foods = new ArrayList<>();
    private HashMap<KeyCode, Boolean> keys = new HashMap<>();
    private Character player;
    private AnimationTimer updateTimer;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        gamePane.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        gamePane.setOnKeyReleased(event -> keys.put(event.getCode(), false));

        gamePane.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observableValue, Bounds bounds, Bounds newBounds){
                topFrame.setWidth(gamePane.getWidth());
                if (bounds.getHeight() != 0) {
                    topFrame.setHeight(topFrame.getHeight() + (newBounds.getHeight() - bounds.getHeight()) * 0.01);
                }
                clock.setLayoutX(topFrame.getWidth() / 2);
                clock.setMinHeight(topFrame.getHeight());
                playerHp.setMinWidth(topFrame.getWidth() * 0.175);
                playerHp.setMinHeight(topFrame.getHeight() / 2);
                playerScore.setLayoutX(playerHp.getMinWidth() + 40.5);
                playerScore.setMinHeight(topFrame.getHeight());
            }
        });

        updateTimer = new AnimationTimer() {        
            @Override
            public void handle(long arg0) {
                update(); 
            }
        };
        player_spawn();
        updateTimer.start();
        change_time(); 
    }

    public void update(){
        check_move();
        isFoodEat();
        check_alive_players();
        check_time();
        food_spawn();
        setHpAndScore();
    }

    public boolean isPressed(KeyCode key){
        return keys.getOrDefault(key, false);
    }

    public void check_move(){
        double y = player.getPosY();
        double x = player.getPosX();
        if (isPressed(KeyCode.UP) && (isPressed(KeyCode.LEFT)) && (y > topFrame.getHeight()) && (x > 0)){
            player.moveY(-1.);
            player.moveX(-1.);
        }
        else if (isPressed(KeyCode.UP) && (isPressed(KeyCode.RIGHT)) && (y > topFrame.getHeight()) && (x < (gamePane.getWidth() - player.getCharacterWidth()))){
            player.moveY(-1.);
            player.moveX(1.);
        }
        else if (isPressed(KeyCode.DOWN) && (isPressed(KeyCode.LEFT)) && (y < (gamePane.getHeight() - player.getCharacterHeight())) && (x > 0) ){
            player.moveY(1.);
            player.moveX(-1.);
        }
        else if (isPressed(KeyCode.DOWN) && (isPressed(KeyCode.RIGHT)) && (y < (gamePane.getHeight() - player.getCharacterHeight())) && (x < (gamePane.getWidth() - player.getCharacterWidth()))){
            player.moveY(1.);
            player.moveX(1.);
        }
        else if (isPressed(KeyCode.UP) && (y > topFrame.getHeight())){
            player.moveY(-1.4);
        }
        else if (isPressed(KeyCode.DOWN) && (y < (gamePane.getHeight() - player.getCharacterHeight()))){
            player.moveY(1.4);
        }
        else if (isPressed(KeyCode.LEFT) && (x > 0)){
            player.moveX(-1.4);
        }
        else if (isPressed(KeyCode.RIGHT) && (x < (gamePane.getWidth() - player.getCharacterWidth()))){
            player.moveX(1.4);
        }
    }

    public void check_alive_players(){
        player.get_damage();
        if (!player.isAlive()){
            gamePane.getChildren().remove(player);
            updateTimer.stop();
            clockThread.interrupt();
            try {
                new SceneSwitcher().switchScene("\\resources\\GameOver.fxml");
                gamePane.getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void check_time(){
        if (clock.getText().equals("0")){
            updateTimer.stop();
        }
    }

    public void player_spawn(){
        int playerWidth = 32;
        int playerHeight = 32;
        double pos_x = Math.floor(playerWidth + Math.random() * (gamePane.getPrefWidth() - playerWidth));
        double pos_y = Math.floor((topFrame.getHeight() + playerHeight) + Math.random() * (gamePane.getPrefHeight() - topFrame.getHeight() - 2 * playerHeight));
        int colorIndex = (int)Math.floor(Math.random() * playerColors.size());
        player = new Character(pos_x, pos_y, playerColors.get(colorIndex));
        playerHp.setProgress(player.getHp());
        gamePane.getChildren().add(player);
    }

    public void food_spawn(){
        double radius = 7;
        int random = (int)Math.floor(Math.random() * 210);
        double pos_x = Math.floor(radius + Math.random() * (gamePane.getWidth() - 2 * radius));
        double pos_y = Math.floor((topFrame.getHeight() + radius) + Math.random() * (gamePane.getHeight() - (topFrame.getHeight() + 2 * radius)));
        if (random == 7){
            Food food = new Food(pos_x, pos_y, radius, Color.RED);
            foods.add(food);
            gamePane.getChildren().add(food);
        }        
    }

    public void setHpAndScore(){
        playerHp.setProgress(player.getHp() / 100);
        playerScore.setText("Score: " + String.valueOf(player.getScore()));
    }

    public void change_time(){
        clock.setText("120");
        int max_time = 120;
        clockThread = new Thread(){
            public void run(){
                for (int i = 0; i < max_time; i++){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        break;
                    }
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
        clockThread.start();
    }

    public void isFoodEat(){
        foods.forEach((food) -> {
            if (player.getBoundsInParent().intersects(food.getBoundsInParent())){
                player.setHp(player.getHp() + food.getFoodValue());
                if (player.getHp() > 100) player.setHp(100);
                player.setScore(player.getScore() + 1);
                removeFood = food;
            }
        });
        foods.remove(removeFood);
        gamePane.getChildren().remove(removeFood);
    }
    
}