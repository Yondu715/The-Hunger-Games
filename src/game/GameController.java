package src.game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import src.SceneSwitcher;
import src.game.client.Food;
import src.game.client.Character;
import src.DB.DatabaseHandler;
import src.DB.Player;

public class GameController implements Initializable{

    @FXML
    Button btn_menu, btn_rest;
    @FXML
    AnchorPane gamePane, gameOverPane;
    @FXML
    Rectangle topFrame;
    @FXML
    Label clock, playerScore, txt_points, txt_message, labelTitle, txt_over;
    @FXML
    ProgressBar playerHp;
    @FXML
    ImageView gif1, gif2, gif3;

    private Thread clockThread;
    private AnimationTimer updateTimer;
    private HashMap<KeyCode, Boolean> keys = new HashMap<>();

    private Food removeFood = null;
    private ArrayList<Food> foods = new ArrayList<>();
    private List<String> foodGifs = Arrays.asList("lapsha", "cake", "kitchen", "cream", "salat");
    private Character player;

    private Singleton loginHandler;
    private DatabaseHandler databaseHandler = DatabaseHandler.getInstance();

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
                    gif1.setLayoutX(gif1.getLayoutX() + (newBounds.getWidth() - bounds.getWidth()) * 0.1);
                    gif2.setLayoutX(gif2.getLayoutX() + (newBounds.getWidth() - bounds.getWidth()) * 0.75);
                    gif3.setLayoutX(gif3.getLayoutX() + (newBounds.getWidth() - bounds.getWidth()) * 0.6);
                    gif1.setLayoutY(gif1.getLayoutY() + (newBounds.getHeight() - bounds.getHeight()) * 0.5);
                    gif2.setLayoutY(gif2.getLayoutY() + (newBounds.getHeight() - bounds.getHeight()) * 0.6);
                    gif3.setLayoutY(gif3.getLayoutY() + (newBounds.getHeight() - bounds.getHeight()) * 0.8);
                }
                clock.setLayoutX(topFrame.getWidth() / 2);
                clock.setMinHeight(topFrame.getHeight());
                playerHp.setMinWidth(topFrame.getWidth() * 0.175);
                playerHp.setMinHeight(topFrame.getHeight() / 2);
                playerScore.setLayoutX(playerHp.getMinWidth() + 40.5);
                playerScore.setMinHeight(topFrame.getHeight());
                gameOverPane.setMinWidth(gamePane.getWidth());
                gameOverPane.setMinHeight(gamePane.getHeight());
                labelTitle.setLayoutX((gamePane.getWidth() - labelTitle.getWidth()) / 2);
                txt_message.setLayoutX((gamePane.getWidth() - txt_message.getWidth()) / 2);
                txt_points.setLayoutX((gamePane.getWidth() - txt_points.getWidth()) / 2);
                txt_over.setLayoutX((gamePane.getWidth() - txt_over.getWidth()) / 2);
                txt_message.setLayoutY(gamePane.getHeight() * 0.275);
                txt_points.setLayoutY(gamePane.getHeight() * 0.475);
                txt_over.setLayoutY(gamePane.getHeight() * 0.675);
            }
        });

        btn_menu.setOnAction(event -> {
            new SceneSwitcher().switchScene("\\resources\\menu.fxml");
            gameOverPane.getScene().getWindow().hide();
        });
        btn_rest.setOnAction(event -> {
            new SceneSwitcher().switchScene("\\resources\\game.fxml");
            gameOverPane.getScene().getWindow().hide();
        });

        player_spawn();
        change_time(); 
        updateTimer = new AnimationTimer() {        
            @Override
            public void handle(long arg0) {
                update(); 
            }
        };
        updateTimer.start();
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
            updateTimer.stop();
            clockThread.interrupt();
            endGame();
        }
    }

    public void check_time(){
        if (clock.getText().equals("0")){
            updateTimer.stop();
            endGame();
        }
    }

    public void player_spawn() {
        int playerWidth = 57;
        int playerHeight = 53;
        double pos_x = Math.floor(playerWidth + Math.random() * (gamePane.getPrefWidth() - 2 * playerWidth));
        double pos_y = Math.floor((topFrame.getHeight() + playerHeight) + Math.random() * (gamePane.getPrefHeight() - topFrame.getHeight() - 2 * playerHeight));
        Image player_image = new Image("\\resources\\gifs\\player.gif");
        player = new Character(pos_x, pos_y, player_image);
        playerHp.setProgress(player.getHp());
        gamePane.getChildren().add(player);
    }

    public void food_spawn(){
        int radius = 32;
        int random = (int)Math.floor(Math.random() * 210);
        double pos_x = Math.floor(radius + Math.random() * (gamePane.getWidth() - 2 * radius));
        double pos_y = Math.floor((topFrame.getHeight() + radius) + Math.random() * (gamePane.getHeight() - (topFrame.getHeight() + 2 * radius)));
        if (random == 7){
            int gifIndex = (int)Math.floor(Math.random() * foodGifs.size());
            Image img = new Image("\\resources\\gifs\\" + foodGifs.get(gifIndex) + ".gif");
            Food food = new Food(pos_x, pos_y, radius, img);
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
        int time = Integer.parseInt(clock.getText());
        clockThread = new Thread(){
            public void run(){
                for (int i = 0; i < time; i++){
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

    public void endGame(){
        txt_over.setText(loginHandler.getCreatedInstance().getLogin());
        txt_points.setText(txt_points.getText() + " " + player.getScore());
        gamePane.getChildren().remove(player);
        foods.forEach((food) -> {
            gamePane.getChildren().remove(food);
        });
        gameOverPane.setVisible(true);
        new Thread(
            () -> databaseHandler.playerScore(loginHandler.getCreatedInstance().getLogin(), player.getScore())
        ).start();
    }
    
}