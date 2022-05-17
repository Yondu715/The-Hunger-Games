package src.database;

import java.sql.ResultSet;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class playerRating {
    
    private SimpleIntegerProperty id;
    private SimpleStringProperty username;
    private SimpleIntegerProperty record;

    public playerRating(int id, String username, int record){
        this.id = new SimpleIntegerProperty(id);
        this.username = new SimpleStringProperty(username);
        this.record = new SimpleIntegerProperty(record);
    }

    public int getId(){
        return this.id.get();
    }

    public String getUsername(){
        return this.username.get();
    }

    public int getScore(){
        return this.record.get();
    }
}
