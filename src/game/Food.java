package src.game;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Food extends Circle {
    private int food_value = 5;

    Food(double pos_x, double pos_y, double radius, Paint fill){
        super(pos_x, pos_y, radius, fill);
    }

    public int getFoodValue(){
        return this.food_value;
    }
}
