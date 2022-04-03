package src.game;

import javafx.scene.shape.Rectangle;

public class Character extends Rectangle{
    double hp = 100;
    int score = 0;
    double pos_x;
    double pos_y;
    static int width = 32;
    static int height = 32;

    public Character(double pos_x, double pos_y){
        super(pos_x, pos_y, width, height);
        this.pos_x = pos_x;
        this.pos_y = pos_y;
    }

    public boolean isAlive(){
        if (hp <= 0){
            return false;
        }
        return true;
    }

    public void get_damage(){
        if (this.hp > 0){
            this.hp -= 0.02;
        }  
    }

    public void moveX(int x){
        boolean duration_x = x>0 ? true:false;
        if (duration_x) this.setTranslateX(this.getTranslateX() + Math.abs(x));
        else this.setTranslateX(this.getTranslateX() - Math.abs(x));
    }

    public void moveY(int y){
        boolean duration_y = y>0 ? true:false;
        if (duration_y) this.setTranslateY(this.getTranslateY() - Math.abs(y));
        else this.setTranslateY(this.getTranslateY() + Math.abs(y));
    }
}
