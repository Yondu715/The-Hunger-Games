package src.game.client;

import java.util.Arrays;
import java.util.List;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

public class Food extends Pane {
    private int food_value = 5;
    private Image img;
    private ImageView imageView;
    private int radius;

    public Food(double pos_x, double pos_y, int radius, Image img){
        this.radius = radius;
        this.img = img;
        setLayoutX(pos_x);
        setLayoutY(pos_y);
        this.imageView = new ImageView(this.img);
        this.imageView.setViewport(new Rectangle2D(0, 0, this.radius, this.radius));
        getChildren().addAll(this.imageView);
    }

    public int getFoodValue(){
        return this.food_value;
    }
}
