package src.DB;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Player {
    private SimpleStringProperty login;
    private SimpleIntegerProperty points;

    public Player(String login, Integer points) {
        this.login = new SimpleStringProperty(login);
        this.points = new SimpleIntegerProperty(points);
    }

    public String getLogin() {
        return this.login.get();
    }

    public int getPoints() {
        return this.points.get();
    }
}
