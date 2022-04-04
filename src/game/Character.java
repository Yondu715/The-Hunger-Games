package src.game;

import javafx.scene.shape.Rectangle;

public class Character extends Rectangle{
    double hp = 100;
    int score = 0;
    double pos_x;
    double pos_y;
    int width = 32;
    int height = 32;
    Food removeFood = null;

    public Character(double pos_x, double pos_y){
        setWidth(width);
        setHeight(height);
        setX(pos_x);
        setY(pos_y);
        this.pos_x = pos_x;
        this.pos_y = pos_y;        
    }

    public double getPosX(){
        return this.pos_x;
    }

    public double getPosY(){
        return this.pos_y;
    }

    public int getCharacterWidth(){
        return this.width;
    }

    public int getCharacterHeight(){
        return this.height;
    }

    public boolean isAlive(){
        if (hp <= 0){
            return false;
        }
        return true;
    }

    public void get_damage(){
        if (this.hp > 0){
            this.hp -= 0.05;
        }
        String hp = String.format("%.2f", this.hp).replace(",", ".");
        this.hp = Double.parseDouble(hp);  
    }

    public void moveX(Double x){
        boolean duration_x = x>0 ? true:false;
        if (duration_x) this.setTranslateX(this.getTranslateX() + Math.abs(x));
        else this.setTranslateX(this.getTranslateX() - Math.abs(x));
        this.pos_x += x;
        isFoodEat();
    }

    public void moveY(Double y){
        boolean duration_y = y>0 ? true:false;
        if (duration_y) this.setTranslateY(this.getTranslateY() + Math.abs(y));
        else this.setTranslateY(this.getTranslateY() - Math.abs(y));
        this.pos_y += y;
        isFoodEat();
    }

    public void isFoodEat(){
        GameController.foods.forEach((food) -> {
            if (this.getBoundsInParent().intersects(food.getBoundsInParent())){
                this.hp += food.food_value;
                if (this.hp > 100) this.hp = 100;
                this.score++;
                removeFood = food;
            }
        });
        GameController.foods.remove(removeFood);
        GameController.rootGame.getChildren().remove(removeFood);
    }
}
